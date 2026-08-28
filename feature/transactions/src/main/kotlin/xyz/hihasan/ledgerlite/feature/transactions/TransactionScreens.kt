package xyz.hihasan.ledgerlite.feature.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import xyz.hihasan.ledgerlite.core.designsystem.component.EmptyState
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerButton
import xyz.hihasan.ledgerlite.core.designsystem.component.LoadingIndicator
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.model.Transaction

@Composable
fun TransactionListRoute(
    onTransactionClick: (String) -> Unit,
    viewModel: TransactionListViewModel = hiltViewModel(),
) {
    val items = viewModel.transactions.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(LedgerTestTags.TRANSACTION_LIST_SCREEN),
    ) {
        when {
            items.loadState.refresh is LoadState.Loading ->
                LoadingIndicator(Modifier.testTag(LedgerTestTags.TRANSACTION_LIST_LOADING))

            items.loadState.refresh is LoadState.NotLoading && items.itemCount == 0 ->
                EmptyState(
                    "No transactions yet",
                    Modifier.testTag(LedgerTestTags.TRANSACTION_LIST_EMPTY),
                )

            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(LedgerTestTags.TRANSACTION_LIST),
            ) {
                items(
                    count = items.itemCount,
                    key = items.itemKey { it.id },
                ) { index ->
                    val tx = items[index] ?: return@items
                    TransactionRow(tx = tx, onClick = { onTransactionClick(tx.id) })
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(tx: Transaction, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(LedgerTestTags.transactionRow(tx.id))
            .padding(16.dp),
    ) {
        Text(tx.description)
        Text("${tx.type.name} · ${tx.currency} ${tx.amount.majorUnits}")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailRoute(
    onBack: () -> Unit,
    viewModel: TransactionDetailViewModel = hiltViewModel(),
) {
    val tx by viewModel.transaction.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_SCREEN),
    ) { padding ->
        val current = tx
        if (current == null) {
            LoadingIndicator(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            ) {
                Text(
                    text = "${current.currency} ${current.amount.majorUnits}",
                    modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_AMOUNT),
                )
                Text(current.description)
                Text(current.category.name)
                Text(current.timestamp.toString())
                LedgerButton(
                    text = "Delete",
                    onClick = { viewModel.delete(onDeleted = onBack) },
                    modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_DELETE_BUTTON),
                )
            }
        }
    }
}
