package xyz.hihasan.ledgerlite.core.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionRepository
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionFilter
import javax.inject.Inject

/** Paged stream of all transactions (Transaction List screen). */
class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(
        filter: TransactionFilter = TransactionFilter(),
    ): Flow<PagingData<Transaction>> = repository.pagedTransactions(filter)
}

/** Paged stream filtered by the Search / Filter criteria. */
class SearchTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(filter: TransactionFilter): Flow<PagingData<Transaction>> =
        repository.pagedTransactions(filter)
}

class GetTransactionDetailUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(id: String): Flow<Transaction?> = repository.observeTransaction(id)
}

/** Generates a large batch of fake transactions for performance testing (10k+ supported). */
class SeedTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    suspend operator fun invoke(count: Int): LedgerResult<Unit> = repository.seed(count)
}
