package xyz.hihasan.ledgerlite.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.hihasan.ledgerlite.core.designsystem.component.EmptyState
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerCard
import xyz.hihasan.ledgerlite.core.designsystem.component.LoadingIndicator
import xyz.hihasan.ledgerlite.core.designsystem.component.SectionHeader
import xyz.hihasan.ledgerlite.core.designsystem.component.SpendingBarChart
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews
import xyz.hihasan.ledgerlite.core.domain.usecase.DashboardData
import xyz.hihasan.ledgerlite.core.model.CategorySpend
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.SpendingSummary
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import java.time.LocalDate

@Composable
fun DashboardRoute(
    onAddTransaction: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DashboardContent(state = state, onAddTransaction = onAddTransaction)
}

/** Stateless Dashboard. [DashboardRoute] just collects [DashboardViewModel.state]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardUiState,
    onAddTransaction: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_SCREEN),
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                modifier = Modifier.testTag(LedgerTestTags.TOP_APP_BAR),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransaction,
                modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_ADD_FAB),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add transaction")
            }
        },
    ) { padding ->
        when (val s = state) {
            DashboardUiState.Loading -> LoadingIndicator(Modifier.padding(padding))
            is DashboardUiState.Error -> EmptyState(s.message, Modifier.padding(padding))
            is DashboardUiState.Ready -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                LedgerCard {
                    SectionHeader("Total balance")
                    Text(
                        text = "${s.data.totalBalance.majorUnits}",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_BALANCE_TEXT),
                    )
                    Text(
                        text = "Spent this month · ${s.data.summary.totalSpent.majorUnits}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                SectionHeader("Spending by category")
                LedgerCard {
                    SpendingBarChart(
                        entries = s.data.summary.byCategory.map {
                            it.category.name to it.total.minorUnits.toFloat()
                        },
                        modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_SPENDING_CHART),
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(LedgerTestTags.DASHBOARD_CATEGORY_LEGEND),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    s.data.summary.byCategory.forEach {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = it.category.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "${it.total.majorUnits}",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Previews ------------------------------------------------------------------

/** Deterministic sample used by the previews and the screenshot tests. */
internal fun sampleDashboardData(): DashboardData = DashboardData(
    totalBalance = Money(732_144),
    summary = SpendingSummary(
        periodStart = LocalDate.of(2026, 8, 1),
        periodEnd = LocalDate.of(2026, 8, 31),
        totalSpent = Money(75_500),
        totalIncome = Money(240_000),
        byCategory = listOf(
            CategorySpend(TransactionCategory.GROCERIES, Money(42_000), 12),
            CategorySpend(TransactionCategory.DINING, Money(18_000), 7),
            CategorySpend(TransactionCategory.TRANSPORT, Money(9_500), 5),
            CategorySpend(TransactionCategory.UTILITIES, Money(6_000), 2),
        ),
    ),
)

@ThemePreviews
@Composable
private fun DashboardReadyPreview() = LedgerTheme {
    DashboardContent(state = DashboardUiState.Ready(sampleDashboardData()), onAddTransaction = {})
}

@ThemePreviews
@Composable
private fun DashboardLoadingPreview() = LedgerTheme {
    DashboardContent(state = DashboardUiState.Loading, onAddTransaction = {})
}

@ThemePreviews
@Composable
private fun DashboardErrorPreview() = LedgerTheme {
    DashboardContent(
        state = DashboardUiState.Error("Failed to load dashboard"),
        onAddTransaction = {},
    )
}
