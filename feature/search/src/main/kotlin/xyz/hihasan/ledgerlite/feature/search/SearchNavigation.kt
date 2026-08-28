package xyz.hihasan.ledgerlite.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search"

fun NavController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(onTransactionClick: (String) -> Unit) {
    composable(SEARCH_ROUTE) {
        SearchRoute(onTransactionClick = onTransactionClick)
    }
}
