package xyz.hihasan.ledgerlite.feature.addexpense

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ADD_TRANSACTION_ROUTE = "add-transaction"

fun NavController.navigateToAddTransaction() = navigate(ADD_TRANSACTION_ROUTE)

fun NavGraphBuilder.addTransactionScreen(onSaved: () -> Unit) {
    composable(ADD_TRANSACTION_ROUTE) {
        AddTransactionRoute(onSaved = onSaved)
    }
}
