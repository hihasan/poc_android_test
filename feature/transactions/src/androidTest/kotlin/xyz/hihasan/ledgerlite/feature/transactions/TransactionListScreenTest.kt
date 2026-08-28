package xyz.hihasan.ledgerlite.feature.transactions

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for the paginated Transaction List. Tags: [LedgerTestTags.TRANSACTION_LIST],
 * [LedgerTestTags.TRANSACTION_LIST_EMPTY], and per-row [LedgerTestTags.transactionRow] `(id)`.
 *
 * For paging content, drive a `Flow<PagingData<Transaction>>` from `PagingData.from(list)`.
 */
class TransactionListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun empty_state_shows_when_there_are_no_transactions() { TODO() }

    @Test
    fun rows_render_for_each_item() { TODO() }

    @Test
    fun scrolling_to_the_bottom_loads_the_next_page() { TODO() }

    @Test
    fun tapping_a_row_invokes_onTransactionClick_with_its_id() { TODO() }
}
