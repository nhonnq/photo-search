package dev.nhonnq.app.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.nhonnq.app.navigation.Graph
import dev.nhonnq.app.navigation.Screen
import dev.nhonnq.app.ui.navigationbar.NavigationBarNestedGraph
import dev.nhonnq.app.ui.navigationbar.NavigationBarScreen
import dev.nhonnq.app.ui.photo.details.PhotoDetailsScreen
import dev.nhonnq.app.ui.photo.details.viewmodel.PhotoDetailsViewModel
import dev.nhonnq.app.util.composableHorizontalSlide

/**
 * Main navigation graph for the application.
 *
 * @param mainNavController The main navigation controller for the app.
 * @param darkMode Boolean indicating if dark mode is enabled.
 * @param onThemeUpdated Callback to be invoked when the theme is updated.
 */
@Composable
fun MainGraph(
    mainNavController: NavHostController,
    darkMode: Boolean,
    onThemeUpdated: () -> Unit
) {
    NavHost(
        navController = mainNavController,
        startDestination = Screen.NavigationBar,
        route = Graph.Main::class
    ) {
        composableHorizontalSlide<Screen.NavigationBar> {
            val nestedNavController = rememberNavController()
            NavigationBarScreen(
                darkMode = darkMode,
                onThemeUpdated = onThemeUpdated
            ) {
                NavigationBarNestedGraph(
                    navController = nestedNavController,
                    mainNavController = mainNavController,
                    parentRoute = Graph.Main::class
                )
            }
        }

        composableHorizontalSlide<Screen.PhotoDetails> {
            val viewModel = hiltViewModel<PhotoDetailsViewModel>()
            PhotoDetailsScreen(
                mainNavController = mainNavController,
                viewModel = viewModel,
            )
        }
    }
}