package com.digrec.hodl.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.currency
import hodl.shared.generated.resources.home
import hodl.shared.generated.resources.settings
import hodl.shared.generated.resources.transactions
import org.jetbrains.compose.resources.StringResource

/**
 * Represents the main destinations within the app.
 *
 * Created by Dejan Igrec
 */
enum class AppDestinations(
    val icon: ImageVector,
    val label: StringResource,
    val route: String,
) {
    HOME(
        icon = Icons.Default.Home,
        label = Res.string.home,
        route = Route.Home.route,
    ),
    CURRENCIES(
        icon = Icons.AutoMirrored.Default.TrendingUp,
        label = Res.string.currency,
        route = Route.Currencies.route,
    ),
    TRANSACTIONS(
        icon = Icons.AutoMirrored.Default.List,
        label = Res.string.transactions,
        route = Route.Transactions.route,
    ),
    SETTINGS(
        icon = Icons.Default.Settings,
        label = Res.string.settings,
        route = Route.Settings.route,
    ),
}
