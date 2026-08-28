package xyz.hihasan.ledgerlite.feature.transactions

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for Transaction Detail. Tags: [LedgerTestTags.TRANSACTION_DETAIL_SCREEN],
 * [LedgerTestTags.TRANSACTION_DETAIL_AMOUNT], [LedgerTestTags.TRANSACTION_DETAIL_DELETE_BUTTON].
 */
class TransactionDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shows_loading_until_the_transaction_arrives() { TODO() }

    @Test
    fun renders_amount_description_and_category() { TODO() }

    @Test
    fun delete_button_triggers_delete_then_onBack() { TODO() }
}
