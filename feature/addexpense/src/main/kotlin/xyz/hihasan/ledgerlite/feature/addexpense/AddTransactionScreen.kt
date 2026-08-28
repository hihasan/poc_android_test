package xyz.hihasan.ledgerlite.feature.addexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerButton
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerTextField
import xyz.hihasan.ledgerlite.core.designsystem.component.SectionHeader
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews
import xyz.hihasan.ledgerlite.core.domain.model.TransactionFormInput
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.AccountType
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType

@Composable
fun AddTransactionRoute(
    onSaved: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val accounts by viewModel.accounts.collectAsStateWithLifecycle()

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onSaved()
    }

    AddTransactionContent(
        state = state,
        accounts = accounts,
        onTypeChange = viewModel::onTypeChange,
        onAmountChange = viewModel::onAmountChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onCategoryChange = viewModel::onCategoryChange,
        onAccountChange = viewModel::onAccountChange,
        onCounterpartyChange = viewModel::onCounterpartyChange,
        onNoteChange = viewModel::onNoteChange,
        onSave = viewModel::save,
    )
}

/** Stateless Add Expense / Transfer form. [AddTransactionRoute] owns the ViewModel + save effect. */
@Composable
fun AddTransactionContent(
    state: AddTransactionUiState,
    accounts: List<Account>,
    onTypeChange: (TransactionType) -> Unit,
    onAmountChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (TransactionCategory) -> Unit,
    onAccountChange: (String) -> Unit,
    onCounterpartyChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onSave: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp)
            .testTag(LedgerTestTags.ADD_TX_SCREEN),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("New transaction", style = MaterialTheme.typography.headlineMedium)

        SectionHeader("Type")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_TYPE_TOGGLE),
        ) {
            TransactionType.entries.forEach { type ->
                FilterChip(
                    selected = state.form.type == type,
                    onClick = { onTypeChange(type) },
                    label = { Text(type.name) },
                )
            }
        }

        LedgerTextField(
            value = state.form.amountText,
            onValueChange = onAmountChange,
            label = "Amount",
            keyboardType = KeyboardType.Decimal,
            errorText = state.fieldErrors["amount"],
            errorTestTag = LedgerTestTags.ADD_TX_AMOUNT_ERROR,
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_AMOUNT_FIELD),
        )

        LedgerTextField(
            value = state.form.description,
            onValueChange = onDescriptionChange,
            label = "Description",
            errorText = state.fieldErrors["description"],
            errorTestTag = LedgerTestTags.ADD_TX_DESCRIPTION_ERROR,
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_DESCRIPTION_FIELD),
        )

        // Minimal category picker (swap for an ExposedDropdownMenu later).
        Column(
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_CATEGORY_DROPDOWN),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SectionHeader("Category · ${state.form.category?.name ?: "none"}")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    TransactionCategory.GROCERIES,
                    TransactionCategory.DINING,
                    TransactionCategory.TRANSPORT,
                ).forEach { category ->
                    FilterChip(
                        selected = state.form.category == category,
                        onClick = { onCategoryChange(category) },
                        label = { Text(category.name) },
                    )
                }
            }
            state.fieldErrors["category"]?.let { Text(it) }
        }

        Column(
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_ACCOUNT_DROPDOWN),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SectionHeader("Account · ${state.form.accountId ?: "none"}")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                accounts.forEach { account ->
                    FilterChip(
                        selected = state.form.accountId == account.id,
                        onClick = { onAccountChange(account.id) },
                        label = { Text(account.name) },
                    )
                }
            }
            state.fieldErrors["account"]?.let { Text(it) }
        }

        if (state.form.type == TransactionType.TRANSFER) {
            Column(
                modifier = Modifier.testTag(LedgerTestTags.ADD_TX_COUNTERPARTY_DROPDOWN),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SectionHeader("To account · ${state.form.counterpartyAccountId ?: "none"}")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    accounts.forEach { account ->
                        FilterChip(
                            selected = state.form.counterpartyAccountId == account.id,
                            onClick = { onCounterpartyChange(account.id) },
                            label = { Text(account.name) },
                        )
                    }
                }
                state.fieldErrors["counterparty"]?.let { Text(it) }
            }
        }

        LedgerTextField(
            value = state.form.note,
            onValueChange = onNoteChange,
            label = "Note (optional)",
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_NOTE_FIELD),
        )

        state.generalError?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }

        LedgerButton(
            text = if (state.isSaving) "Saving…" else "Save",
            onClick = onSave,
            enabled = !state.isSaving,
            modifier = Modifier.testTag(LedgerTestTags.ADD_TX_SAVE_BUTTON),
        )
    }
}

// --- Previews ------------------------------------------------------------------

internal fun sampleAccounts(): List<Account> = listOf(
    Account("acc-checking", "Checking", AccountType.CHECKING, "USD", Money(532_144)),
    Account("acc-savings", "Savings", AccountType.SAVINGS, "USD", Money(1_200_000)),
)

@ThemePreviews
@Composable
private fun AddTransactionContentPreview() = LedgerTheme {
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

@ThemePreviews
@Composable
private fun AddTransactionContentErrorsPreview() = LedgerTheme {
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

@ThemePreviews
@Composable
private fun AddTransactionContentTransferPreview() = LedgerTheme {
    AddTransactionContent(
        state = AddTransactionUiState(
            form = TransactionFormInput(
                type = TransactionType.TRANSFER,
                amountText = "250.00",
                description = "Move to savings",
                accountId = "acc-checking",
                counterpartyAccountId = "acc-savings",
            ),
        ),
        accounts = sampleAccounts(),
        onTypeChange = {}, onAmountChange = {}, onDescriptionChange = {}, onCategoryChange = {},
        onAccountChange = {}, onCounterpartyChange = {}, onNoteChange = {}, onSave = {},
    )
}
