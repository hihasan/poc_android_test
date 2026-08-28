package xyz.hihasan.ledgerlite.feature.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.hihasan.ledgerlite.core.domain.usecase.DeleteTransactionUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.GetTransactionDetailUseCase
import xyz.hihasan.ledgerlite.core.model.Transaction
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getDetail: GetTransactionDetailUseCase,
    private val deleteTransaction: DeleteTransactionUseCase,
) : ViewModel() {

    val transactionId: String = checkNotNull(savedStateHandle[ARG_TRANSACTION_ID])

    val transaction: StateFlow<Transaction?> = getDetail(transactionId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun delete(onDeleted: () -> Unit) {
        viewModelScope.launch {
            deleteTransaction(transactionId)
            onDeleted()
        }
    }

    companion object {
        const val ARG_TRANSACTION_ID = "transactionId"
    }
}
