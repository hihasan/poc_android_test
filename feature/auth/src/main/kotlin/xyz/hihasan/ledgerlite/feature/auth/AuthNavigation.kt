package xyz.hihasan.ledgerlite.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "auth/login"
const val REGISTER_ROUTE = "auth/register"

fun NavController.navigateToLogin(builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(LOGIN_ROUTE, builder)

fun NavController.navigateToRegister() = navigate(REGISTER_ROUTE)

fun NavGraphBuilder.authScreens(
    onAuthenticated: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onBack: () -> Unit,
) {
    composable(LOGIN_ROUTE) {
        LoginRoute(onAuthenticated = onAuthenticated, onNavigateToRegister = onNavigateToRegister)
    }
    composable(REGISTER_ROUTE) {
        RegisterRoute(onAuthenticated = onAuthenticated, onBack = onBack)
    }
}
