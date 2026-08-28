package xyz.hihasan.ledgerlite.feature.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerTextField
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.model.TransactionType

@Composable
fun SearchRoute(
    onTransactionClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    val results = viewModel.results.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(LedgerTestTags.SEARCH_SCREEN),
    ) {
        LedgerTextField(
            value = filter.query,
            onValueChange = viewModel::onQueryChange,
            label = "Search transactions",
            modifier = Modifier.testTag(LedgerTestTags.SEARCH_QUERY_FIELD),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .testTag(LedgerTestTags.SEARCH_FILTER_BUTTON),
        ) {
            TransactionType.entries.forEach { type ->
                FilterChip(
                    selected = type in filter.types,
                    onClick = { viewModel.toggleType(type) },
                    label = { Text(type.name) },
                    modifier = Modifier
                        .padding(end = 8.dp)
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
                Text(
                    text = "${tx.description} — ${tx.currency} ${tx.amount.majorUnits}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LedgerTestTags.transactionRow(tx.id))
                        .padding(12.dp),
                )
            }
        }
    }
}
