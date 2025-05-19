package dev.nhonnq.app.ui.photo.details.state

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dev.nhonnq.app.navigation.Screen
import dev.nhonnq.domain.entities.PhotoDetails
import javax.inject.Inject

data class PhotoDetailsState(
    var photoData: PhotoDetails? = null,
    var isLoading: Boolean? = false
)

class PhotoDetailsBundle @Inject constructor(
    savedStateHandle: SavedStateHandle
) {
    val photoId: Int = savedStateHandle.toRoute<Screen.PhotoDetails>().id
}