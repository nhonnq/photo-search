package dev.nhonnq.app.ui.photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.nhonnq.app.R
import dev.nhonnq.app.ui.theme.colors
import dev.nhonnq.domain.entities.PhotoEntity

/**
 * A composable function that represents a list of photos.
 *
 * @param photos The list of photos to display.
 * @param isLoading Boolean indicating if the list is loading.
 * @param onPhotoClick Callback function to be invoked when a photo is clicked.
 */
@Composable
fun PhotoList(
    photos: LazyPagingItems<PhotoEntity>,
    isLoading: Boolean,
    onPhotoClick: (id: Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val gridState = rememberLazyGridState()

    // Clear focus (and hide keyboard) when user scrolls
    LaunchedEffect(gridState.isScrollInProgress) {
        if (gridState.isScrollInProgress) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    // Check photos is empty state
    if (photos.itemCount == 0 && !isLoading) {
        Column(Modifier.padding(8.dp, 16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            // Displayed empty state
            Icon(
                imageVector = Icons.Filled.SentimentDissatisfied,
                contentDescription = "no data",
                tint = Color.LightGray,
                modifier = Modifier.size(48.dp),
            )
            Text(stringResource(R.string.no_data_found))
        }
        return
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        state = gridState
    ) {
        items(photos.itemCount) { index ->
            val photo = photos[index]
            photo?.let {
                PhotoItem(
                    id = it.id,
                    imageUrl = it.src?.small,
                    onPhotoClick = onPhotoClick
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            when (photos.loadState.append) {
                is LoadState.Loading -> {
                    LoadMore()
                }

                is LoadState.Error -> {
                    val error = (photos.loadState.append as LoadState.Error).error
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Error loading more: ${error.localizedMessage}")
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { photos.retry() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun LoadMore() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(32.dp),
            color = colors.secondary,
            trackColor = colors.surfaceVariant,
        )
        Text("Load more")
    }
}

@Preview
@Composable
fun LoadMorePreview() {
    LoadMore()
}