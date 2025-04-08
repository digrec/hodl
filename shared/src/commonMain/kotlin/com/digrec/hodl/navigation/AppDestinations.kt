package com.digrec.hodl.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.home
import hodl.shared.generated.resources.settings
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
    HOME(Icons.Default.Home, Res.string.home, Route.Home.route),
    SETTINGS(Icons.Default.Settings, Res.string.settings, Route.Settings.route),
}
