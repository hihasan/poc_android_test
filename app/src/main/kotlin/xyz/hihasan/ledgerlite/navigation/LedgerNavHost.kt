package xyz.hihasan.ledgerlite.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.feature.addexpense.addTransactionScreen
import xyz.hihasan.ledgerlite.feature.addexpense.navigateToAddTransaction
import xyz.hihasan.ledgerlite.feature.auth.LOGIN_ROUTE
import xyz.hihasan.ledgerlite.feature.auth.authScreens
import xyz.hihasan.ledgerlite.feature.auth.navigateToRegister
import xyz.hihasan.ledgerlite.feature.dashboard.DASHBOARD_ROUTE
import xyz.hihasan.ledgerlite.feature.dashboard.dashboardScreen
import xyz.hihasan.ledgerlite.feature.search.SEARCH_ROUTE
import xyz.hihasan.ledgerlite.feature.search.searchScreen
import xyz.hihasan.ledgerlite.feature.settings.SETTINGS_ROUTE
import xyz.hihasan.ledgerlite.feature.settings.settingsScreen
import xyz.hihasan.ledgerlite.feature.transactions.TRANSACTION_LIST_ROUTE
import xyz.hihasan.ledgerlite.feature.transactions.navigateToTransactionDetail
import xyz.hihasan.ledgerlite.feature.transactions.transactionDetailScreen
import xyz.hihasan.ledgerlite.feature.transactions.transactionListScreen

private data class TopLevelDestination(val route: String, val label: String, val tag: String)

private val TOP_LEVEL = listOf(
    TopLevelDestination(DASHBOARD_ROUTE, "Home", "bottom_nav_dashboard"),
    TopLevelDestination(TRANSACTION_LIST_ROUTE, "Activity", "bottom_nav_transactions"),
    TopLevelDestination(SEARCH_ROUTE, "Search", "bottom_nav_search"),
    TopLevelDestination(SETTINGS_ROUTE, "Settings", "bottom_nav_settings"),
)

@Composable
fun LedgerNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in TOP_LEVEL.map { it.route }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(modifier = Modifier.testTag(LedgerTestTags.BOTTOM_NAV)) {
                    val destination = backStackEntry?.destination
                    TOP_LEVEL.forEach { item ->
                        NavigationBarItem(
                            selected = destination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(item.label.take(1)) },
                            label = { Text(item.label) },
                            modifier = Modifier.testTag(item.tag),
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = LOGIN_ROUTE,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            authScreens(
                onAuthenticated = {
                    navController.navigate(DASHBOARD_ROUTE) {
                        popUpTo(LOGIN_ROUTE) { inclusive = true }
                    }
                },
                onNavigateToRegister = navController::navigateToRegister,
                onBack = { navController.popBackStack() },
            )

            dashboardScreen(onAddTransaction = navController::navigateToAddTransaction)

            transactionListScreen(
                onTransactionClick = navController::navigateToTransactionDetail,
            )
            transactionDetailScreen(onBack = { navController.popBackStack() })

            addTransactionScreen(onSaved = { navController.popBackStack() })

            searchScreen(onTransactionClick = navController::navigateToTransactionDetail)

            settingsScreen(
                onLoggedOut = {
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}
