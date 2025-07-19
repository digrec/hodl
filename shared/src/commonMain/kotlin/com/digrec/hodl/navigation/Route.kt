package com.digrec.hodl.navigation

/**
 * Centralized navigation route definitions for the app.
 *
 * @property route The string representation of the navigation route
 *
 * Created by Dejan Igrec
 */
sealed class Route(val route: String) {
    data object Home : Route(route = "/home")

    data object Currencies : Route(route = "/currencies")

    data object Transactions : Route(route = "/transactions")
    data object Settings : Route(route = "/settings")
}
