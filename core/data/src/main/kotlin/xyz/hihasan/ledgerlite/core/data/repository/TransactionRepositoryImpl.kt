package xyz.hihasan.ledgerlite.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.data.fake.FakeDataFactory
import xyz.hihasan.ledgerlite.core.data.mapper.toDomain
import xyz.hihasan.ledgerlite.core.data.mapper.toEntity
import xyz.hihasan.ledgerlite.core.data.mapper.toRequest
import xyz.hihasan.ledgerlite.core.database.dao.AccountDao
import xyz.hihasan.ledgerlite.core.database.dao.TransactionDao
import xyz.hihasan.ledgerlite.core.domain.model.NewTransaction
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionRepository
import xyz.hihasan.ledgerlite.core.model.CategorySpend
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.SpendingSummary
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.model.TransactionCategory
import xyz.hihasan.ledgerlite.core.model.TransactionFilter
import xyz.hihasan.ledgerlite.core.model.TransactionSort
import xyz.hihasan.ledgerlite.core.network.api.LedgerApi
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val api: LedgerApi,
) : TransactionRepository {

    override fun pagedTransactions(filter: TransactionFilter): Flow<PagingData<Transaction>> =
        Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false)) {
            if (filter.isEmpty) {
                transactionDao.pagingSourceNewestFirst()
            } else {
                transactionDao.pagingSourceFiltered(
                    query = filter.query,
                    filterTypes = if (filter.types.isEmpty()) 0 else 1,
                    types = filter.types.map { it.name },
                    filterCategories = if (filter.categories.isEmpty()) 0 else 1,
                    categories = filter.categories.map { it.name },
                    minAmount = filter.minAmount?.minorUnits,
                    maxAmount = filter.maxAmount?.minorUnits,
                    from = filter.from?.toEpochMilli(),
                    to = filter.to?.toEpochMilli(),
                    sort = filter.sort.toColumnIndex(),
                )
            }
        }.flow.map { paging -> paging.map { it.toDomain() } }

    override fun observeTransactions(filter: TransactionFilter): Flow<List<Transaction>> =
        transactionDao.observeRecent(RECENT_LIMIT).map { list -> list.map { it.toDomain() } }

    override fun observeTransaction(id: String): Flow<Transaction?> =
        transactionDao.observeById(id).map { it?.toDomain() }

    override fun observeSpendingSummary(from: LocalDate, to: LocalDate): Flow<SpendingSummary> {
        val fromMillis = from.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val toMillis = to.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return combine(
            transactionDao.categoryTotals("EXPENSE", fromMillis, toMillis),
            transactionDao.totalByType("EXPENSE", fromMillis, toMillis),
            transactionDao.totalByType("INCOME", fromMillis, toMillis),
        ) { categoryTotals, spent, income ->
            SpendingSummary(
                periodStart = from,
                periodEnd = to,
                totalSpent = Money(spent),
                totalIncome = Money(income),
                byCategory = categoryTotals.map {
                    CategorySpend(
                        category = TransactionCategory.entries.firstOrNull { c -> c.name == it.category }
                            ?: TransactionCategory.OTHER,
                        total = Money(it.totalMinorUnits),
                        transactionCount = it.txCount,
                    )
                },
            )
        }
    }

    override suspend fun addTransaction(draft: NewTransaction): LedgerResult<Transaction> {
        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            type = draft.type,
            category = draft.category,
            amount = draft.amount,
            currency = draft.currency,
            description = draft.description,
            note = draft.note,
            timestamp = draft.timestamp,
            accountId = draft.accountId,
            counterpartyAccountId = draft.counterpartyAccountId,
            pending = false,
        )
        return try {
            transactionDao.upsert(transaction.toEntity())
            runCatching { api.createTransaction(draft.toRequest()) } // best-effort sync
            LedgerResult.Success(transaction)
        } catch (t: Throwable) {
            LedgerResult.Failure(LedgerError.Unknown(t))
        }
    }

    override suspend fun deleteTransaction(id: String): LedgerResult<Unit> = try {
        transactionDao.deleteById(id)
        LedgerResult.Success(Unit)
    } catch (t: Throwable) {
        LedgerResult.Failure(LedgerError.Unknown(t))
    }

    override suspend fun refresh(): LedgerResult<Unit> = try {
        val page = api.getTransactions(page = 1, pageSize = PAGE_SIZE)
        transactionDao.upsertAll(page.items.map { it.toEntity() })
        LedgerResult.Success(Unit)
    } catch (t: Throwable) {
        LedgerResult.Failure(LedgerError.Network(t.message))
    }

    override suspend fun seed(count: Int): LedgerResult<Unit> = try {
        val factory = FakeDataFactory()
        accountDao.upsertAll(
            factory.accounts.map {
                xyz.hihasan.ledgerlite.core.database.entity.AccountEntity(
                    id = it.id,
                    name = it.name,
                    type = it.type.name,
                    currency = it.currency,
                    balanceMinorUnits = it.balance.minorUnits,
                )
            },
        )
        factory.transactions(count).chunked(SEED_CHUNK).forEach { chunk ->
            transactionDao.upsertAll(chunk.map { it.toEntity() })
        }
        LedgerResult.Success(Unit)
    } catch (t: Throwable) {
        LedgerResult.Failure(LedgerError.Unknown(t))
    }

    private fun TransactionSort.toColumnIndex(): Int = when (this) {
        TransactionSort.NEWEST_FIRST -> 0
        TransactionSort.OLDEST_FIRST -> 1
        TransactionSort.AMOUNT_DESC -> 2
        TransactionSort.AMOUNT_ASC -> 3
    }

    private companion object {
        const val PAGE_SIZE = 20
        const val RECENT_LIMIT = 50
        const val SEED_CHUNK = 500
    }
}
