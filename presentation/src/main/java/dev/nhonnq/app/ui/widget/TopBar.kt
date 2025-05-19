package dev.nhonnq.app.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nhonnq.app.util.preview.PreviewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    darkMode: Boolean,
    fontFamily: FontFamily = FontFamily.Default,
    fontSize: TextUnit = 25.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
    onThemeUpdated: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    fontWeight = fontWeight
                )
            },
            actions = {
                IconButton(
                    onClick = { onThemeUpdated() }
                ) {
                    val icon = if (darkMode) {
                        Icons.Filled.DarkMode
                    } else {
                        Icons.Outlined.DarkMode
                    }
                    Icon(imageVector = icon, contentDescription = "Dark Mode")
                }
            }
        )
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TopBarPreview() {
    PreviewContainer {
        Column {
            TopBar("Photo Search", true, onThemeUpdated = {})
            Spacer(modifier = Modifier.padding(10.dp))
            TopBar("Photo Search", false, onThemeUpdated = {})
        }
    }
}