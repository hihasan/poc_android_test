package xyz.hihasan.ledgerlite.core.model

import java.time.Instant

enum class TransactionType { EXPENSE, INCOME, TRANSFER }

enum class TransactionCategory {
    GROCERIES, DINING, TRANSPORT, HOUSING, UTILITIES, ENTERTAINMENT,
    HEALTH, SHOPPING, TRAVEL, INCOME, TRANSFER, OTHER
}

/** A single ledger entry. */
data class Transaction(
    val id: String,
    val type: TransactionType,
    val category: TransactionCategory,
    val amount: Money,
    val currency: String,
    val description: String,
    val note: String? = null,
    val timestamp: Instant,
    val accountId: String,
    val counterpartyAccountId: String? = null,
    val pending: Boolean = false,
)
