package com.digrec.hodl.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource

/**
 * Provides adaptive main navigation for the application.
 *
 * Created by Dejan Igrec
 */
@Composable
fun NavigationScaffold(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                val selected = currentDestination?.route == destination.route
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.label),
                        )
                    },
                    label = {
                        Text(stringResource(destination.label))
                    },
                    selected = selected,
                    onClick = {
                        if (!selected) navHostController.navigateTo(destination.route)
                    }
                )
            }
        }
    ) {
        Navigation(
            navHostController = navHostController,
            modifier = modifier.fillMaxSize(),
        )
    }
}

/**
 * Navigates to a destination route with proper state management.
 *
 * Prevents duplicate destinations, preserves state, and ensures
 * correct back stack behavior when navigating between main destinations.
 */
private fun NavHostController.navigateTo(route: String) {
    navigate(route) {
        graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
