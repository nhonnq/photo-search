package dev.nhonnq.app.ui.photo

//noinspection UsingMaterialAndMaterial3Libraries
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.nhonnq.app.util.preview.PreviewContainer

@Composable
fun PhotoItem(
    id: Int,
    imageUrl: String?,
    onPhotoClick: (id: Int) -> Unit = {},
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onPhotoClick(id) }
                .background(Color.LightGray) // fallback bg while loading
        )
    }
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PhotoItemPreview() {
    PreviewContainer {
        Surface {
            PhotoItem(
                id=1,
                imageUrl = "https://www.pexels.com/photo/hot-air-balloon-2325447/"
            )
        }
    }
}