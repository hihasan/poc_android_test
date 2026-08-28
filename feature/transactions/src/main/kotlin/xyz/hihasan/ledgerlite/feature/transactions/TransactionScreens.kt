package xyz.hihasan.ledgerlite.feature.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import xyz.hihasan.ledgerlite.core.designsystem.component.EmptyState
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerButton
import xyz.hihasan.ledgerlite.core.designsystem.component.LoadingIndicator
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.time.Instant

@Composable
fun TransactionListRoute(
    onTransactionClick: (String) -> Unit,
    viewModel: TransactionListViewModel = hiltViewModel(),
) {
    val items = viewModel.transactions.collectAsLazyPagingItems()
    TransactionListContent(items = items, onTransactionClick = onTransactionClick)
}

/** Stateless paged transaction list. [TransactionListRoute] just collects the paging flow. */
@Composable
fun TransactionListContent(
    items: LazyPagingItems<Transaction>,
    onTransactionClick: (String) -> Unit,
) {
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
    TransactionDetailContent(
        transaction = tx,
        onDelete = { viewModel.delete(onDeleted = onBack) },
    )
}

/** Stateless transaction detail. `null` [transaction] renders the loading state. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailContent(
    transaction: Transaction?,
    onDelete: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_SCREEN),
    ) { padding ->
        if (transaction == null) {
            LoadingIndicator(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            ) {
                Text(
                    text = "${transaction.currency} ${transaction.amount.majorUnits}",
                    modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_AMOUNT),
                )
                Text(transaction.description)
                Text(transaction.category.name)
                Text(transaction.timestamp.toString())
                LedgerButton(
                    text = "Delete",
                    onClick = onDelete,
                    modifier = Modifier.testTag(LedgerTestTags.TRANSACTION_DETAIL_DELETE_BUTTON),
                )
            }
        }
    }
}

// --- Previews ------------------------------------------------------------------

private fun sampleTransactions(): List<Transaction> = listOf(
    Transaction(
        id = "tx-1",
        type = TransactionType.EXPENSE,
        category = TransactionCategory.GROCERIES,
        amount = Money(4_250),
        currency = "USD",
        description = "Weekly groceries",
        timestamp = Instant.parse("2026-08-20T10:15:00Z"),
        accountId = "acc-checking",
    ),
    Transaction(
        id = "tx-2",
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME,
        amount = Money(240_000),
        currency = "USD",
        description = "Payroll",
        timestamp = Instant.parse("2026-08-15T09:00:00Z"),
        accountId = "acc-checking",
    ),
    Transaction(
        id = "tx-3",
        type = TransactionType.TRANSFER,
        category = TransactionCategory.TRANSFER,
        amount = Money(50_000),
        currency = "USD",
        description = "Move to savings",
        timestamp = Instant.parse("2026-08-12T18:30:00Z"),
        accountId = "acc-checking",
        counterpartyAccountId = "acc-savings",
    ),
)

@Composable
private fun rememberPreviewPagingItems(
    data: List<Transaction>,
    refresh: LoadState = LoadState.NotLoading(endOfPaginationReached = true),
): LazyPagingItems<Transaction> {
    val pagingData = PagingData.from(
        data = data,
        sourceLoadStates = LoadStates(
            refresh = refresh,
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
        ),
    )
    return flowOf(pagingData).collectAsLazyPagingItems()
}

@ThemePreviews
@Composable
private fun TransactionListContentPreview() = LedgerTheme {
    TransactionListContent(
        items = rememberPreviewPagingItems(sampleTransactions()),
        onTransactionClick = {},
    )
}

@ThemePreviews
@Composable
private fun TransactionListEmptyPreview() = LedgerTheme {
    TransactionListContent(
        items = rememberPreviewPagingItems(emptyList()),
        onTransactionClick = {},
    )
}

@ThemePreviews
@Composable
private fun TransactionListLoadingPreview() = LedgerTheme {
    TransactionListContent(
        items = rememberPreviewPagingItems(emptyList(), refresh = LoadState.Loading),
        onTransactionClick = {},
    )
}

@ThemePreviews
@Composable
private fun TransactionRowPreview() = LedgerTheme {
    Surface {
        Column {
            sampleTransactions().forEach { tx ->
                TransactionRow(tx = tx, onClick = {})
                HorizontalDivider()
            }
        }
    }
}

@ThemePreviews
@Composable
private fun TransactionDetailContentPreview() = LedgerTheme {
    TransactionDetailContent(transaction = sampleTransactions().first(), onDelete = {})
}

@ThemePreviews
@Composable
private fun TransactionDetailLoadingPreview() = LedgerTheme {
    TransactionDetailContent(transaction = null, onDelete = {})
}
