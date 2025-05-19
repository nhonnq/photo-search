package dev.nhonnq.app.ui.navigationbar

import dev.nhonnq.app.ui.photo.PhotoListScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.nhonnq.app.navigation.Screen
import dev.nhonnq.app.ui.main.MainRouter
import dev.nhonnq.app.ui.photo.viewmodel.PhotosViewModel
import dev.nhonnq.app.util.composableHorizontalSlide
import kotlin.reflect.KClass

@Composable
fun NavigationBarNestedGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    parentRoute: KClass<*>?
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PhotoList,
        route = parentRoute
    ) {
        // Main router for photos screen
        composableHorizontalSlide<Screen.PhotoList> {
            val viewModel = hiltViewModel<PhotosViewModel>()
            PhotoListScreen(
                mainRouter = MainRouter(mainNavController),
                viewModel = viewModel
            )
        }
    }
}