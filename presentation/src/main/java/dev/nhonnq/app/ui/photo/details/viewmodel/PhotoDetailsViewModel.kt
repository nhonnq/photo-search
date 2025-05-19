package dev.nhonnq.app.ui.photo.details.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nhonnq.app.ui.base.BaseViewModel
import dev.nhonnq.app.ui.photo.details.state.PhotoDetailsBundle
import dev.nhonnq.app.ui.photo.details.state.PhotoDetailsState
import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.usecase.GetPhotoDetailsUseCase
import dev.nhonnq.domain.util.Result
import dev.nhonnq.domain.util.onError
import dev.nhonnq.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val getPhotoDetailsUseCase: GetPhotoDetailsUseCase,
    photoDetailsBundle: PhotoDetailsBundle,
) : BaseViewModel() {

    // UI state exposed to the UI
    private val _uiState: MutableStateFlow<PhotoDetailsState> = MutableStateFlow(PhotoDetailsState())
    val uiState = _uiState.asStateFlow()

    private val _fullscreenPhotoUrl = MutableStateFlow<String?>(null)
    val fullscreenPhotoUrl: StateFlow<String?> = _fullscreenPhotoUrl

    // Get photo ID from bundle
    private val photoId: Int = photoDetailsBundle.photoId

    init {
        onInitialState()
    }

    // Initial State to get photo details
    private fun onInitialState() = launch {
        _uiState.value.isLoading = true
        // Get photo details from remote server
        getPhotoDetailsById(photoId).onSuccess {
            _uiState.value = PhotoDetailsState(photoData = it, false)
        }.onError {
            _uiState.value.isLoading = false
        }
    }

    /*
     * Get photo details from remote server
     */
    private suspend fun getPhotoDetailsById(id: Int): Result<PhotoDetails> = getPhotoDetailsUseCase(id)

    fun onOpenFullscreen(url: String) {
        _fullscreenPhotoUrl.value = url
    }

    fun onCloseFullscreen() {
        _fullscreenPhotoUrl.value = null
    }

}