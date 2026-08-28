package xyz.hihasan.ledgerlite.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [Index("timestamp"), Index("category"), Index("type"), Index("accountId")],
)
data class TransactionEntity(
    @PrimaryKey val id: String,
    val type: String,
    val category: String,
    val amountMinorUnits: Long,
    val currency: String,
    val description: String,
    val note: String?,
    val timestamp: Long,
    val accountId: String,
    val counterpartyAccountId: String?,
    val pending: Boolean,
)

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val currency: String,
    val balanceMinorUnits: Long,
)

/** Projection returned by the category-aggregation query. */
data class CategoryTotal(
    val category: String,
    val totalMinorUnits: Long,
    val txCount: Int,
)
