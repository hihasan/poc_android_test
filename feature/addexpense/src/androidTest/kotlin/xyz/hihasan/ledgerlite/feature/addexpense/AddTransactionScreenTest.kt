package xyz.hihasan.ledgerlite.feature.addexpense

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for the Add Expense / Transfer form. Tags: [LedgerTestTags.ADD_TX_SCREEN],
 * [LedgerTestTags.ADD_TX_AMOUNT_FIELD], [LedgerTestTags.ADD_TX_AMOUNT_ERROR],
 * [LedgerTestTags.ADD_TX_TYPE_TOGGLE], [LedgerTestTags.ADD_TX_SAVE_BUTTON].
 */
class AddTransactionScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun save_with_empty_amount_shows_the_amount_error() { TODO() }

    @Test
    fun selecting_TRANSFER_reveals_the_counterparty_picker() { TODO() }

    @Test
    fun a_valid_form_saves_and_invokes_onSaved() { TODO() }
}
