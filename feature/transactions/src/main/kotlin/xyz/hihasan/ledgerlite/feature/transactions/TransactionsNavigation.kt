package xyz.hihasan.ledgerlite.feature.transactions

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

const val TRANSACTION_LIST_ROUTE = "transactions"
const val TRANSACTION_DETAIL_ROUTE = "transactions/{transactionId}"

/** Also reachable from the "transaction added" notification. */
const val TRANSACTION_DETAIL_DEEP_LINK = "ledgerlite://transaction/{transactionId}"

fun transactionDetailRoute(id: String): String = "transactions/$id"

fun NavController.navigateToTransactionList() = navigate(TRANSACTION_LIST_ROUTE)
fun NavController.navigateToTransactionDetail(id: String) = navigate(transactionDetailRoute(id))

fun NavGraphBuilder.transactionListScreen(onTransactionClick: (String) -> Unit) {
    composable(TRANSACTION_LIST_ROUTE) {
        TransactionListRoute(onTransactionClick = onTransactionClick)
    }
}

fun NavGraphBuilder.transactionDetailScreen(onBack: () -> Unit) {
    composable(
        route = TRANSACTION_DETAIL_ROUTE,
        arguments = listOf(navArgument("transactionId") { type = NavType.StringType }),
        deepLinks = listOf(navDeepLink { uriPattern = TRANSACTION_DETAIL_DEEP_LINK }),
    ) {
        TransactionDetailRoute(onBack = onBack)
    }
}
