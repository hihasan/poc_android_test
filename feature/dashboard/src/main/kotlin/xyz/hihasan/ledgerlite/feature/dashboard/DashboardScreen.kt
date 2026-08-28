package xyz.hihasan.ledgerlite.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import xyz.hihasan.ledgerlite.core.designsystem.component.LoadingIndicator
import xyz.hihasan.ledgerlite.core.designsystem.component.SpendingBarChart
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRoute(
    onAddTransaction: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Balance: ${s.data.totalBalance.majorUnits}",
                    modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_BALANCE_TEXT),
                )
                Text("Spent this month: ${s.data.summary.totalSpent.majorUnits}")
                SpendingBarChart(
                    entries = s.data.summary.byCategory.map {
                        it.category.name to it.total.minorUnits.toFloat()
                    },
                    modifier = Modifier.testTag(LedgerTestTags.DASHBOARD_SPENDING_CHART),
                )
                Column(Modifier.testTag(LedgerTestTags.DASHBOARD_CATEGORY_LEGEND)) {
                    s.data.summary.byCategory.forEach { Text("${it.category.name}: ${it.total.majorUnits}") }
                }
            }
        }
    }
}
