package com.digrec.hodl.navigation

/**
 * Centralized navigation route definitions for the app.
 *
 * @property route The string representation of the navigation route
 */
sealed class Route(val route: String) {
    data object Greeting : Route(route = "/greeting")
}
