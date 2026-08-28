package xyz.hihasan.ledgerlite.feature.addexpense

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.domain.model.TransactionFormInput
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType

/**
 * Compose Preview Screenshot tests for the Add Expense / Transfer form.
 *   * `:feature:addexpense:updateDebugScreenshotTest`   — record reference PNGs
 *   * `:feature:addexpense:validateDebugScreenshotTest` — diff against them (CI)
 * Each non-private `@Preview` is a test case; they render the real stateless
 * [AddTransactionContent] with the shared [sampleAccounts] fixture.
 */
@Preview(name = "Add expense · light", showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddExpenseLightSnapshot() {
    LedgerTheme(darkTheme = false) {
        AddTransactionContent(
            state = AddTransactionUiState(
                form = TransactionFormInput(
                    type = TransactionType.EXPENSE,
                    amountText = "42.50",
                    description = "Weekly groceries",
                    category = TransactionCategory.GROCERIES,
                    accountId = "acc-checking",
                ),
            ),
            accounts = sampleAccounts(),
            onTypeChange = {}, onAmountChange = {}, onDescriptionChange = {}, onCategoryChange = {},
            onAccountChange = {}, onCounterpartyChange = {}, onNoteChange = {}, onSave = {},
        )
    }
}

@Preview(
    name = "Add expense · dark",
    showBackground = true,
    widthDp = 400,
    heightDp = 900,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AddExpenseDarkSnapshot() {
    LedgerTheme(darkTheme = true) {
        AddTransactionContent(
            state = AddTransactionUiState(
                form = TransactionFormInput(
                    type = TransactionType.EXPENSE,
                    amountText = "42.50",
                    description = "Weekly groceries",
                    category = TransactionCategory.GROCERIES,
                    accountId = "acc-checking",
                ),
            ),
            accounts = sampleAccounts(),
            onTypeChange = {}, onAmountChange = {}, onDescriptionChange = {}, onCategoryChange = {},
            onAccountChange = {}, onCounterpartyChange = {}, onNoteChange = {}, onSave = {},
        )
    }
}

@Preview(name = "Add expense · validation errors", showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddExpenseErrorsSnapshot() {
    LedgerTheme(darkTheme = false) {
        AddTransactionContent(
            state = AddTransactionUiState(
                form = TransactionFormInput(type = TransactionType.EXPENSE),
                fieldErrors = mapOf(
                    "amount" to "Enter an amount",
                    "description" to "Description is required",
                    "category" to "Pick a category",
                ),
                generalError = "Could not save",
            ),
            accounts = sampleAccounts(),
            onTypeChange = {}, onAmountChange = {}, onDescriptionChange = {}, onCategoryChange = {},
            onAccountChange = {}, onCounterpartyChange = {}, onNoteChange = {}, onSave = {},
        )
    }
}
