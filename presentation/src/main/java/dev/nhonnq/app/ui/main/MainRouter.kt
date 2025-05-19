package dev.nhonnq.app.ui.main

import androidx.navigation.NavHostController
import dev.nhonnq.app.navigation.Screen

class MainRouter(
    private val mainNavController: NavHostController
) {

    fun navigateToPhotoDetails(id: Int) {
        mainNavController.navigate(Screen.PhotoDetails(id))
    }
}