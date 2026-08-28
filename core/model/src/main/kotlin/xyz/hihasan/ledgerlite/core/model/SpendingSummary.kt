package xyz.hihasan.ledgerlite.core.model

import java.time.LocalDate

data class CategorySpend(
    val category: TransactionCategory,
    val total: Money,
    val transactionCount: Int,
)

/** Aggregated numbers backing the Dashboard screen. */
data class SpendingSummary(
    val periodStart: LocalDate,
    val periodEnd: LocalDate,
    val totalSpent: Money,
    val totalIncome: Money,
    val byCategory: List<CategorySpend>,
) {
    val net: Money get() = totalIncome - totalSpent
}
