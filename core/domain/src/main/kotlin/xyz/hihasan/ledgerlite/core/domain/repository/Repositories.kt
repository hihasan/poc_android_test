package xyz.hihasan.ledgerlite.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.model.NewTransaction
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.AuthSession
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.SpendingSummary
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionFilter
import java.time.LocalDate

interface AuthRepository {
    val session: Flow<AuthSession?>
    suspend fun login(email: String, password: String): LedgerResult<AuthSession>
    suspend fun register(email: String, password: String, displayName: String): LedgerResult<AuthSession>
    suspend fun logout()
}

interface AccountRepository {
    fun observeAccounts(): Flow<List<Account>>
    fun observeTotalBalance(): Flow<Money>
    suspend fun refresh(): LedgerResult<Unit>
}

interface TransactionRepository {
    fun pagedTransactions(filter: TransactionFilter): Flow<PagingData<Transaction>>
    fun observeTransactions(filter: TransactionFilter): Flow<List<Transaction>>
    fun observeTransaction(id: String): Flow<Transaction?>
    fun observeSpendingSummary(from: LocalDate, to: LocalDate): Flow<SpendingSummary>
    suspend fun addTransaction(draft: NewTransaction): LedgerResult<Transaction>
    suspend fun deleteTransaction(id: String): LedgerResult<Unit>
    suspend fun refresh(): LedgerResult<Unit>
    suspend fun seed(count: Int): LedgerResult<Unit>
}

interface SettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setDarkTheme(enabled: Boolean)
    suspend fun setBiometricUnlock(enabled: Boolean)
    suspend fun setDefaultCurrency(code: String)
}

data class AppSettings(
    val darkTheme: Boolean = false,
    val biometricUnlock: Boolean = false,
    val defaultCurrency: String = "USD",
)

/** Port implemented by :core:notifications; called after a transaction is persisted. */
interface TransactionEventNotifier {
    fun onTransactionAdded(transaction: Transaction)
}
