package xyz.hihasan.ledgerlite.core.data.fake

import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.AccountType
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * Deterministic fake-data generator. Seeded [Random] so a given (seed, count) always yields the
 * same records — important for stable performance/screenshot baselines. Generating 10k+
 * transactions is supported and cheap.
 */
class FakeDataFactory(private val seed: Long = 42L) {

    val accounts: List<Account> = listOf(
        Account("acc-checking", "Everyday Checking", AccountType.CHECKING, "USD", Money.ofMajor(5_321.44)),
        Account("acc-savings", "Rainy Day Savings", AccountType.SAVINGS, "USD", Money.ofMajor(12_000.00)),
        Account("acc-credit", "Rewards Card", AccountType.CREDIT_CARD, "USD", Money.ofMajor(-842.10)),
        Account("acc-cash", "Wallet", AccountType.CASH, "USD", Money.ofMajor(120.00)),
    )

    fun transactions(count: Int, now: Instant = Instant.now()): List<Transaction> {
        val rng = Random(seed)
        val merchants = MERCHANTS
        return List(count) { i ->
            val type = when {
                i % 20 == 0 -> TransactionType.INCOME
                i % 11 == 0 -> TransactionType.TRANSFER
                else -> TransactionType.EXPENSE
            }
            val category = when (type) {
                TransactionType.INCOME -> TransactionCategory.INCOME
                TransactionType.TRANSFER -> TransactionCategory.TRANSFER
                else -> EXPENSE_CATEGORIES[rng.nextInt(EXPENSE_CATEGORIES.size)]
            }
            val account = accounts[rng.nextInt(accounts.size)]
            val counterparty =
                if (type == TransactionType.TRANSFER) {
                    accounts.filter { it.id != account.id }[rng.nextInt(accounts.size - 1)].id
                } else {
                    null
                }
            val minor = when (type) {
                TransactionType.INCOME -> rng.nextLong(150_000, 600_000)
                TransactionType.TRANSFER -> rng.nextLong(5_000, 120_000)
                else -> rng.nextLong(199, 28_000)
            }
            Transaction(
                id = "txn-%06d".format(i),
                type = type,
                category = category,
                amount = Money(minor),
                currency = "USD",
                description = merchants[rng.nextInt(merchants.size)],
                note = if (rng.nextInt(6) == 0) "Recurring" else null,
                timestamp = now.minus(i.toLong(), ChronoUnit.HOURS)
                    .minus(rng.nextLong(0, 59), ChronoUnit.MINUTES),
                accountId = account.id,
                counterpartyAccountId = counterparty,
                pending = i < 3,
            )
        }
    }

    private companion object {
        val EXPENSE_CATEGORIES = listOf(
            TransactionCategory.GROCERIES, TransactionCategory.DINING, TransactionCategory.TRANSPORT,
            TransactionCategory.HOUSING, TransactionCategory.UTILITIES, TransactionCategory.ENTERTAINMENT,
            TransactionCategory.HEALTH, TransactionCategory.SHOPPING, TransactionCategory.TRAVEL,
            TransactionCategory.OTHER,
        )
        val MERCHANTS = listOf(
            "Whole Foods", "Blue Bottle Coffee", "Shell", "Uber", "Netflix", "Spotify",
            "Amazon", "Costco", "Trader Joe's", "Chipotle", "Delta Air Lines", "CVS Pharmacy",
            "Apple", "Steam", "IKEA", "The Home Depot", "Lyft", "Starbucks", "Target", "Sweetgreen",
        )
    }
}
