package xyz.hihasan.ledgerlite.feature.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerTextField
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionFilter
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.time.Instant

@Composable
fun SearchRoute(
    onTransactionClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    val results = viewModel.results.collectAsLazyPagingItems()

    SearchContent(
        filter = filter,
        results = results,
        onQueryChange = viewModel::onQueryChange,
        onToggleType = viewModel::toggleType,
    )
}

/** Stateless Search screen. [SearchRoute] owns the ViewModel, debounce, and paging flow. */
@Composable
fun SearchContent(
    filter: TransactionFilter,
    results: LazyPagingItems<Transaction>,
    onQueryChange: (String) -> Unit,
    onToggleType: (TransactionType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(LedgerTestTags.SEARCH_SCREEN),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LedgerTextField(
            value = filter.query,
            onValueChange = onQueryChange,
            label = "Search transactions",
            modifier = Modifier.testTag(LedgerTestTags.SEARCH_QUERY_FIELD),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .testTag(LedgerTestTags.SEARCH_FILTER_BUTTON),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TransactionType.entries.forEach { type ->
                FilterChip(
                    selected = type in filter.types,
                    onClick = { onToggleType(type) },
                    label = { Text(type.name) },
                    modifier = Modifier
                        .testTag(LedgerTestTags.SEARCH_TYPE_CHIP_PREFIX + type.name),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(LedgerTestTags.SEARCH_RESULTS_LIST),
        ) {
            items(
                count = results.itemCount,
                key = results.itemKey { it.id },
            ) { index ->
                val tx = results[index] ?: return@items
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LedgerTestTags.transactionRow(tx.id))
                        .padding(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = tx.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "${tx.currency} ${tx.amount.majorUnits}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

// --- Previews ------------------------------------------------------------------

private fun sampleResults(): List<Transaction> = listOf(
    Transaction(
        id = "tx-1",
        type = TransactionType.EXPENSE,
        category = TransactionCategory.DINING,
        amount = Money(3_180),
        currency = "USD",
        description = "Dinner with friends",
        timestamp = Instant.parse("2026-08-21T20:00:00Z"),
        accountId = "acc-checking",
    ),
    Transaction(
        id = "tx-2",
        type = TransactionType.EXPENSE,
        category = TransactionCategory.TRANSPORT,
        amount = Money(950),
        currency = "USD",
        description = "Metro card top-up",
        timestamp = Instant.parse("2026-08-19T08:30:00Z"),
        accountId = "acc-checking",
    ),
)

@Composable
private fun rememberPreviewPagingItems(data: List<Transaction>): LazyPagingItems<Transaction> {
    val pagingData = PagingData.from(
        data = data,
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
        ),
    )
    return flowOf(pagingData).collectAsLazyPagingItems()
}

@ThemePreviews
@Composable
private fun SearchContentPreview() = LedgerTheme {
    SearchContent(
        filter = TransactionFilter(query = "din"),
        results = rememberPreviewPagingItems(sampleResults()),
        onQueryChange = {},
        onToggleType = {},
    )
}

@ThemePreviews
@Composable
private fun SearchContentFilteredPreview() = LedgerTheme {
    SearchContent(
        filter = TransactionFilter(query = "", types = setOf(TransactionType.EXPENSE, TransactionType.INCOME)),
        results = rememberPreviewPagingItems(sampleResults()),
        onQueryChange = {},
        onToggleType = {},
    )
}

@ThemePreviews
@Composable
private fun SearchContentEmptyPreview() = LedgerTheme {
    SearchContent(
        filter = TransactionFilter(query = "xyzzy"),
        results = rememberPreviewPagingItems(emptyList()),
        onQueryChange = {},
        onToggleType = {},
    )
}
