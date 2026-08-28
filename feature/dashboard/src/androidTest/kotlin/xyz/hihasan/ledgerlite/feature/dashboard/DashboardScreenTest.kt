package xyz.hihasan.ledgerlite.feature.dashboard

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for the Dashboard. Tags: [LedgerTestTags.DASHBOARD_SCREEN],
 * [LedgerTestTags.DASHBOARD_BALANCE_TEXT], [LedgerTestTags.DASHBOARD_SPENDING_CHART],
 * [LedgerTestTags.DASHBOARD_ADD_FAB].
 */
class DashboardScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shows_loading_indicator_first() { TODO() }

    @Test
    fun renders_balance_and_chart_when_ready() { TODO() }

    @Test
    fun tapping_the_fab_invokes_onAddTransaction() { TODO() }
}
