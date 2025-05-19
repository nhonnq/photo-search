package dev.nhonnq.app.navigation

import kotlinx.serialization.Serializable

/**
 * Defines the navigation structure for the application.
 */
sealed class Screen {
    @Serializable
    data object NavigationBar : Screen()

    @Serializable
    data object PhotoList : Screen()

    @Serializable
    data class PhotoDetails(val id: Int) : Screen()
}

sealed class Graph {
    @Serializable
    data object Main : Graph()
}