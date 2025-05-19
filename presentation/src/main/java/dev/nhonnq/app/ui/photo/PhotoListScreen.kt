package dev.nhonnq.app.ui.photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.nhonnq.app.ui.main.MainRouter
import dev.nhonnq.app.ui.photo.state.PhotosNavigationState
import dev.nhonnq.app.ui.photo.state.PhotosUiState
import dev.nhonnq.app.ui.photo.viewmodel.PhotosViewModel
import dev.nhonnq.app.ui.search.SearchBar
import dev.nhonnq.app.ui.widget.PullToRefresh
import dev.nhonnq.app.util.collectAsEffect
import dev.nhonnq.domain.entities.PhotoEntity

/**
 * A composable function that represents the photo list screen.
 *
 * @param mainRouter The main router for navigation.
 * @param viewModel The view model for managing UI state and actions.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoListScreen(
    mainRouter: MainRouter,
    viewModel: PhotosViewModel
) {
    val query by viewModel.searchQuery.collectAsState()
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val searchHistories = viewModel.searchHistories.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState =
        rememberPullRefreshState(uiState.showLoading, { viewModel.onRefresh() })

    viewModel.navigationState.collectAsEffect { navigationState ->
        when (navigationState) {
            is PhotosNavigationState.PhotoDetails -> mainRouter.navigateToPhotoDetails(
                navigationState.id
            )
        }
    }
    viewModel.refreshListState.collectAsEffect {
        photos.refresh()
    }

    LaunchedEffect(key1 = photos.loadState) {
        viewModel.onLoadStateUpdate(photos.loadState)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔍 Search Bar
        SearchBar(
            query = query,
            searchHistories = searchHistories.value,
            onQueryChange = { viewModel.onQueryChange(it) },
            onSearch = {
                println("Search: $query")
                viewModel.onSearch()
            },
            onClear = { viewModel.onClear() },
            onHistoryItemClick = {
                viewModel.onHistoryItemClick(it)
            }
        )

        PullToRefresh(state = pullToRefreshState, refresh = uiState.showLoading) {
            PhotosScreenContainer(
                photos = photos,
                uiState = uiState,
                onPhotoClick = viewModel::onPhotoClick
            )
        }
    }
}

@Composable
private fun PhotosScreenContainer(
    photos: LazyPagingItems<PhotoEntity>,
    uiState: PhotosUiState,
    onPhotoClick: (id: Int) -> Unit
) {
    Surface {
        Column {
            // 📸 Photo List
            when (val state = photos.loadState.refresh) {
                is LoadState.Loading -> Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> {
                    val e = state.error
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Error: ${e.localizedMessage}", color = Color.Red)
                        Button(onClick = { photos.retry() }) {
                            Text("Retry")
                        }
                    }
                }

                else -> {
                    PhotoList(photos, uiState.showLoading, onPhotoClick)
                }
            }
        }
    }
}