package dev.nhonnq.app.ui.photo.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.nhonnq.app.R
import dev.nhonnq.app.ui.photo.details.state.PhotoDetailsState
import dev.nhonnq.app.ui.photo.details.viewmodel.PhotoDetailsViewModel
import dev.nhonnq.app.ui.widget.ShimmerBox
import dev.nhonnq.app.util.preview.PreviewContainer
import dev.nhonnq.domain.entities.PhotoDetails
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import dev.nhonnq.app.ui.widget.FullscreenZoomImage
import dev.nhonnq.app.ui.widget.LinkedUrl

/**
 * A composable function that represents the photo details screen.
 *
 * @param mainNavController The main navigation controller for the app.
 * @param viewModel The view model for managing UI state and actions.
 */
@Composable
fun PhotoDetailsScreen(
    mainNavController: NavHostController,
    viewModel: PhotoDetailsViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    val fullscreenPhotoUrl by viewModel.fullscreenPhotoUrl.collectAsState()
    PhotoDetailsScreenContainer(
        mainNavController,
        state,
        fullscreenPhotoUrl,
        onOpenFullscreen = { url ->
            viewModel.onOpenFullscreen(url)
        },
        onCloseFullscreen = {
            viewModel.onCloseFullscreen()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreenContainer(
    appNavController: NavHostController? = null,
    state: PhotoDetailsState,
    fullscreenPhotoUrl: String? = null,
    onOpenFullscreen: (url: String) -> Unit = {},
    onCloseFullscreen: () -> Unit = {},
) {
    val photo = state.photoData
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.photo_details)) },
                navigationIcon = {
                    IconButton(onClick = { appNavController?.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box {
            Column(modifier = Modifier
                .fillMaxSize().padding(paddingValues)
                .padding(16.dp)) {

                photo?.let {
                    photo.src?.let {
                        AsyncImage(
                            model = it.medium,
                            contentDescription = photo.alt,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onOpenFullscreen(it.original ?: "") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Photographer: ${photo.photographer}", style = MaterialTheme.typography.titleMedium)
                    LinkedUrl(
                        url = photo.photographerUrl ?: "",
                        color = Color.Blue,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${photo.alt}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Size: ${photo.width} x ${photo.height}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = "Average Color: ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        photo.avgColor?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(it.toColorInt())
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))

                    LinkedUrl(
                        url = photo.url ?: "",
                        color = Color.Blue
                    )
                }
            }
        }

        // Show shimmer while API fetching photo details data
        if (state.isLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize().padding(paddingValues)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        fullscreenPhotoUrl?.let {
            FullscreenZoomImage(
                url = fullscreenPhotoUrl,
                onClose = { onCloseFullscreen() }
            )
        }
    }
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PhotoDetailsScreenPreview() {
    PreviewContainer {
        Surface {
            PhotoDetailsScreenContainer(
                state = PhotoDetailsState(
                    photoData = PhotoDetails(
                        id = 1,
                        width = 1024,
                        height = 1024,
                        photographer = "Test",
                        photographerUrl = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
                        url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
                        src = null,
                        alt = "Test",
                        avgColor = "#ff0000"
                    )
                )
            )
        }
    }
}