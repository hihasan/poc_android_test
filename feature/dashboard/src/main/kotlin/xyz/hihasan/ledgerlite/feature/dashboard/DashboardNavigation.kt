package xyz.hihasan.ledgerlite.feature.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val DASHBOARD_ROUTE = "dashboard"

fun NavGraphBuilder.dashboardScreen(onAddTransaction: () -> Unit) {
    composable(DASHBOARD_ROUTE) {
        DashboardRoute(onAddTransaction = onAddTransaction)
    }
}
