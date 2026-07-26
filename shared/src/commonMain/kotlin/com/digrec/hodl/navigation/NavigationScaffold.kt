package com.digrec.hodl.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.digrec.hodl.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

/**
 * Provides adaptive main navigation for the application.
 *
 * Created by Dejan Igrec
 */
@Composable
fun NavigationScaffold(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination

    NavigationScaffoldContent(
        currentRoute = currentDestination?.route,
        onNavigateTo = { route -> navHostController.navigateTo(route) },
        modifier = modifier,
    ) {
        Navigation(navHostController = navHostController, modifier = Modifier.fillMaxSize())
    }
}

/** Stateless adaptive navigation scaffold content for previewability and UI separation. */
@Composable
fun NavigationScaffoldContent(
    currentRoute: String?,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                val selected = currentRoute == destination.route
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.label),
                        )
                    },
                    label = { Text(stringResource(destination.label)) },
                    selected = selected,
                    onClick = { if (!selected) onNavigateTo(destination.route) },
                )
            }
        },
    ) {
        content()
    }
}

/**
 * Navigates to a destination route with proper state management.
 *
 * Prevents duplicate destinations, preserves state, and ensures correct back stack behavior when
 * navigating between main destinations.
 */
private fun NavHostController.navigateTo(route: String) {
    navigate(route) {
        graph.startDestinationRoute?.let { popUpTo(it) { saveState = true } }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
private fun NavigationScaffoldPreview() {
    AppTheme {
        NavigationScaffoldContent(currentRoute = AppDestinations.HOME.route, onNavigateTo = {}) {
            // Empty placeholder for navigation container content
        }
    }
}
