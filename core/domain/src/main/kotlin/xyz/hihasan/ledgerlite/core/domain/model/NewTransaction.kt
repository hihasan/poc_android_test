package xyz.hihasan.ledgerlite.core.domain.model

import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.time.Instant

/** A validated transaction ready to be persisted (produced by AddTransactionUseCase). */
data class NewTransaction(
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Money,
    val currency: String,
    val description: String,
    val note: String?,
    val timestamp: Instant,
    val accountId: String,
    val counterpartyAccountId: String?,
)

/** Raw, unvalidated form input from the Add Expense / Transfer screen. */
data class TransactionFormInput(
    val type: TransactionType = TransactionType.EXPENSE,
    val amountText: String = "",
    val currency: String = "USD",
    val category: TransactionCategory? = null,
    val description: String = "",
    val note: String = "",
    val accountId: String? = null,
    val counterpartyAccountId: String? = null,
    val dateEpochMillis: Long? = null,
)
