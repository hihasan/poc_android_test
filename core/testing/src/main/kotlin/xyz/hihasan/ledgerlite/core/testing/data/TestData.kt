package xyz.hihasan.ledgerlite.core.testing.data

import xyz.hihasan.ledgerlite.core.data.fake.FakeDataFactory
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.time.Instant

/** Convenience factories for tests. [FakeDataFactory] is re-exported for large batches. */
object TestData {

    val fake = FakeDataFactory(seed = 1L)

    fun transaction(
        id: String = "txn-test",
        type: TransactionType = TransactionType.EXPENSE,
        category: TransactionCategory = TransactionCategory.GROCERIES,
        amountMinor: Long = 4_99,
        description: String = "Test purchase",
        timestamp: Instant = Instant.parse("2026-01-15T10:15:30Z"),
        accountId: String = "acc-checking",
    ): Transaction = Transaction(
        id = id,
        type = type,
        category = category,
        amount = Money(amountMinor),
        currency = "USD",
        description = description,
        note = null,
        timestamp = timestamp,
        accountId = accountId,
        counterpartyAccountId = null,
        pending = false,
    )

    fun manyTransactions(count: Int): List<Transaction> = fake.transactions(count)
}
