package dev.nhonnq.app.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nhonnq.app.R
import dev.nhonnq.app.ui.theme.colors
import dev.nhonnq.data.entities.SearchHistory

/**
 * A composable function that represents a search bar with a text field and optional search history.
 */
@Composable
fun SearchBar(
    query: String,
    searchHistories: List<SearchHistory>? = null,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit = {},
    onClear: () -> Unit = {},
    onHistoryItemClick: (SearchHistory) -> Unit = {},
    placeholder: String = stringResource(R.string.search_by_name)
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val expanded = remember { mutableStateOf(false) }

    TextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
            expanded.value = it.isNotBlank() // show history when typing
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                    onClear()
                    expanded.value = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        placeholder = {
            Text(text = placeholder)
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = colors.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            // Hide keyboard and clear focus
            focusManager.clearFocus()
            keyboardController?.hide()
            onSearch()
            expanded.value = false
        })
    )

    if (expanded.value && !searchHistories.isNullOrEmpty()) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            searchHistories
                .filter { it.query.contains(query, ignoreCase = true) }
                .distinct()
                .take(5)
                .forEach { historyItem ->
                    Text(
                        text = historyItem.query,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                onHistoryItemClick(historyItem)
                                expanded.value = false
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                    )
                    Divider(
                        Modifier.padding(0.dp, 8.dp)
                    )
                }
        }
    }

}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        query = "",
        onQueryChange = {}
    )
}