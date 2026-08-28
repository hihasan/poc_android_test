package xyz.hihasan.ledgerlite.core.model

import java.time.Instant

enum class TransactionSort { NEWEST_FIRST, OLDEST_FIRST, AMOUNT_DESC, AMOUNT_ASC }

/** Criteria for the Search / Filter screen. */
data class TransactionFilter(
    val query: String = "",
    val types: Set<TransactionType> = emptySet(),
    val categories: Set<TransactionCategory> = emptySet(),
    val accountIds: Set<String> = emptySet(),
    val minAmount: Money? = null,
    val maxAmount: Money? = null,
    val from: Instant? = null,
    val to: Instant? = null,
    val sort: TransactionSort = TransactionSort.NEWEST_FIRST,
) {
    val isEmpty: Boolean
        get() = query.isBlank() && types.isEmpty() && categories.isEmpty() &&
            accountIds.isEmpty() && minAmount == null && maxAmount == null &&
            from == null && to == null
}
