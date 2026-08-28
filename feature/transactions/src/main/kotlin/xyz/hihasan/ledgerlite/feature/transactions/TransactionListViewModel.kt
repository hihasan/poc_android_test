package xyz.hihasan.ledgerlite.feature.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.domain.usecase.GetTransactionsUseCase
import xyz.hihasan.ledgerlite.core.model.Transaction
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    getTransactions: GetTransactionsUseCase,
) : ViewModel() {

    val transactions: Flow<PagingData<Transaction>> =
        getTransactions().cachedIn(viewModelScope)
}
