package com.digrec.hodl.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.digrec.hodl.feature.currency.ui.CurrenciesScreen
import com.digrec.hodl.feature.home.ui.HomeScreen
import com.digrec.hodl.feature.settings.ui.SettingsScreen
import com.digrec.hodl.feature.transactions.ui.TransactionsScreen

/**
 * Implements the application's navigation graph.
 *
 * @param navHostController Controller that manages app navigation
 * @param modifier Optional modifier for the navigation container
 *
 * Created by Dejan Igrec
 */
@Composable
fun Navigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Home.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() },
        modifier = modifier.fillMaxSize(),
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Route.Transactions.route) {
            TransactionsScreen(navHostController = navHostController)
        }
        composable(route = Route.Currencies.route) {
            CurrenciesScreen()
        }
        composable(route = Route.Settings.route) {
            SettingsScreen(navHostController = navHostController)
        }
    }
}
