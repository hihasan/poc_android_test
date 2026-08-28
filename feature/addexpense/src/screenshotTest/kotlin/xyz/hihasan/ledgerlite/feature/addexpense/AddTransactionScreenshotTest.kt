package xyz.hihasan.ledgerlite.feature.addexpense

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme

/**
 * Compose Preview Screenshot tests for the Add Expense / Transfer form.
 *   * `:feature:addexpense:updateDebugScreenshotTest`   — record reference PNGs
 *   * `:feature:addexpense:validateDebugScreenshotTest` — diff against them (CI)
 * Each non-private `@Preview` is a test case.
 *
 * TODO: render a stateless `AddTransactionContent(...)` with a fixed form + a variant that shows
 * validation errors (so the error tags are captured).
 */
@Preview(name = "Add expense · light", showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddExpenseLightSnapshot() {
    LedgerTheme(darkTheme = false) {
        Text("TODO: AddTransactionContent(state = AddTransactionUiState())")
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
        Text("TODO: AddTransactionContent(state = AddTransactionUiState())")
    }
}

@Preview(name = "Add expense · validation errors", showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddExpenseErrorsSnapshot() {
    LedgerTheme(darkTheme = false) {
        Text("TODO: AddTransactionContent(state = ...fieldErrors = mapOf(\"amount\" to \"Required\"))")
    }
}
