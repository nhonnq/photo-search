package dev.nhonnq.app.ui.photo.state

data class PhotosUiState(
    val showLoading: Boolean = true,
    val errorMessage: String? = null,
)

sealed class PhotosNavigationState {
    data class PhotoDetails(val id: Int) : PhotosNavigationState()
}
