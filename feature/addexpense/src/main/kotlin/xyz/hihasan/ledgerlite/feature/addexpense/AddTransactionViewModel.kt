package xyz.hihasan.ledgerlite.feature.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.model.TransactionFormInput
import xyz.hihasan.ledgerlite.core.domain.usecase.AddTransactionUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.GetAccountsUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.ValidateTransactionFormUseCase
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType
import javax.inject.Inject

data class AddTransactionUiState(
    val form: TransactionFormInput = TransactionFormInput(),
    val fieldErrors: Map<String, String> = emptyMap(),
    val isSaving: Boolean = false,
    val savedSuccessfully: Boolean = false,
    val generalError: String? = null,
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    getAccounts: GetAccountsUseCase,
    private val validateForm: ValidateTransactionFormUseCase,
    private val addTransaction: AddTransactionUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionUiState())
    val state: StateFlow<AddTransactionUiState> = _state.asStateFlow()

    val accounts: StateFlow<List<Account>> = getAccounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onTypeChange(type: TransactionType) = updateForm { it.copy(type = type) }
    fun onAmountChange(value: String) = updateForm { it.copy(amountText = value) }
    fun onDescriptionChange(value: String) = updateForm { it.copy(description = value) }
    fun onNoteChange(value: String) = updateForm { it.copy(note = value) }
    fun onCategoryChange(category: TransactionCategory) = updateForm { it.copy(category = category) }
    fun onAccountChange(id: String) = updateForm { it.copy(accountId = id) }
    fun onCounterpartyChange(id: String) = updateForm { it.copy(counterpartyAccountId = id) }

    /** Runs validation without saving — handy for live field errors. */
    fun validateOnly() {
        val result = validateForm(_state.value.form)
        _state.update {
            it.copy(
                fieldErrors = (result as? LedgerResult.Failure)
                    ?.let { f -> (f.error as? LedgerError.Validation)?.fieldErrors }
                    .orEmpty(),
            )
        }
    }

    fun save() {
        _state.update { it.copy(isSaving = true, generalError = null, fieldErrors = emptyMap()) }
        viewModelScope.launch {
            when (val result = addTransaction(_state.value.form)) {
                is LedgerResult.Success ->
                    _state.update { it.copy(isSaving = false, savedSuccessfully = true) }

                is LedgerResult.Failure -> _state.update {
                    when (val error = result.error) {
                        is LedgerError.Validation ->
                            it.copy(isSaving = false, fieldErrors = error.fieldErrors)
                        else ->
                            it.copy(isSaving = false, generalError = error.message ?: "Could not save")
                    }
                }
            }
        }
    }

    private fun updateForm(block: (TransactionFormInput) -> TransactionFormInput) =
        _state.update { it.copy(form = block(it.form)) }
}
