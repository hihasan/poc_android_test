package xyz.hihasan.ledgerlite.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings() = navigate(SETTINGS_ROUTE)

fun NavGraphBuilder.settingsScreen(onLoggedOut: () -> Unit) {
    composable(SETTINGS_ROUTE) {
        SettingsRoute(onLoggedOut = onLoggedOut)
    }
}
