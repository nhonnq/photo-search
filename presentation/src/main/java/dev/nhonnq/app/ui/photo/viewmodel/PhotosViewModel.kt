package dev.nhonnq.app.ui.photo.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nhonnq.app.ui.base.BaseViewModel
import dev.nhonnq.app.ui.photo.state.PhotosNavigationState
import dev.nhonnq.app.ui.photo.state.PhotosUiState
import dev.nhonnq.app.util.singleSharedFlow
import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.data.usercase.GetAllHistories
import dev.nhonnq.data.usercase.SaveHistory
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.usecase.SearchPhotosUseCase
import dev.nhonnq.domain.util.NetworkMonitor
import dev.nhonnq.domain.util.onSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val getAllHistories: GetAllHistories,
    private val saveHistory: SaveHistory
) : BaseViewModel() {

    // UI State
    private val _uiState: MutableStateFlow<PhotosUiState> = MutableStateFlow(PhotosUiState())
    val uiState = _uiState.asStateFlow()

    // Navigation State
    private val _navigationState: MutableSharedFlow<PhotosNavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    // Refresh List State
    private val _refreshListState: MutableSharedFlow<Unit> = singleSharedFlow()
    val refreshListState = _refreshListState.asSharedFlow()

    // Search Query State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Search Photos State
    private val _photos = MutableStateFlow<PagingData<PhotoEntity>>(PagingData.empty())
    val photos: StateFlow<PagingData<PhotoEntity>> = _photos.asStateFlow()

    // Search Histories State
    private val _searchHistories = MutableStateFlow<List<SearchHistory>?>(null)
    val searchHistories: StateFlow<List<SearchHistory>?> = _searchHistories.asStateFlow()

    // Flag to skip debounce flow after manual search
    private val suppressDebounceOnce = MutableStateFlow(false)

    /**
     * Initialize ViewModel
     */
    init {
        observeQueryDebounce()
        observeNetworkStatus()
        observerLocalDataSource()
    }

    /**
     * Handle search query change event
     */
    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        if (newQuery.isBlank()) {
            _photos.value = PagingData.empty()
        }
    }

    /**
     * Handle search event
     */
    fun onSearch() {
        val query = _searchQuery.value
        if (query.isBlank()) return

        // Suppress debounce flow once
        suppressDebounceOnce.value = true

        viewModelScope.launch {
            searchPhotosUseCase(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _photos.value = pagingData
                }
        }
    }

    /**
     * Handle search history item click event
     */
    fun onHistoryItemClick(item: SearchHistory) {
        _searchQuery.value = item.query
        onSearch()
    }

    /**
     * Handle clear search
     */
    fun onClear() {
        _searchQuery.value = ""
        _photos.value = PagingData.empty()
    }

    /**
     * Handle observe local data source
     */
    private fun observerLocalDataSource() {
        viewModelScope.launch {
            getAllHistories().onSuccess {
                _searchHistories.value = it
            }
        }
    }

    /**
     * Observer search query debounce
     */
    private fun observeQueryDebounce() {
        viewModelScope.launch {
            _searchQuery
                .debounce(2000)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (suppressDebounceOnce.value) {
                        suppressDebounceOnce.value = false
                        emptyFlow()
                    } else {
                        if (query.isBlank()) emptyFlow()
                        else searchPhotosUseCase(query)
                    }
                }
                .cachedIn(viewModelScope)
                .collectLatest { photos ->
                    // Save search query to local database
                    saveHistory(SearchHistory(
                        query = _searchQuery.value,
                        createdAt = System.currentTimeMillis()
                    ))
                    // Add search query to history if not already present
                    if (searchHistories.value?.any { it.query == _searchQuery.value } != true) {
                        // push search query to history
                        _searchHistories.value = searchHistories.value?.plus(
                            SearchHistory(
                                query = _searchQuery.value,
                                createdAt = System.currentTimeMillis()
                            )
                        )
                    }
                    _photos.value = photos
                }
        }
    }

    /**
     * Observe network status and refresh list when network is available
     */
    private fun observeNetworkStatus() {
        networkMonitor.networkState
            .onEach { if (it.shouldRefresh) onRefresh() }
            .launchIn(viewModelScope)
    }

    /**
     * Handle photo click event
     */
    fun onPhotoClick(id: Int) =
        _navigationState.tryEmit(PhotosNavigationState.PhotoDetails(id))

    /**
     * Handle load state update
     */
    fun onLoadStateUpdate(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else -> null
        }

        _uiState.update { it.copy(showLoading = showLoading, errorMessage = error) }
    }

    /**
     * Handle refresh event
     */
    fun onRefresh() = launch {
        _refreshListState.emit(Unit)
    }
}
