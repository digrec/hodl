package com.digrec.hodl.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.digrec.hodl.feature.greeting.ui.GreetingScreen

/**
 * Implements the application's navigation graph.
 *
 * @param navHostController Controller that manages app navigation
 * @param modifier Optional modifier for the navigation container
 */
@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Greeting.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        modifier = modifier.fillMaxSize(),
    ) {
        composable(route = Route.Greeting.route) {
            GreetingScreen(navHostController = navHostController)
        }
    }
}
