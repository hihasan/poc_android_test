package xyz.hihasan.ledgerlite.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.data.mapper.toDomain
import xyz.hihasan.ledgerlite.core.data.mapper.toEntity
import xyz.hihasan.ledgerlite.core.database.dao.AccountDao
import xyz.hihasan.ledgerlite.core.domain.repository.AccountRepository
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.network.api.LedgerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val api: LedgerApi,
) : AccountRepository {

    override fun observeAccounts(): Flow<List<Account>> =
        accountDao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeTotalBalance(): Flow<Money> =
        accountDao.observeTotalBalance().map { Money(it) }

    override suspend fun refresh(): LedgerResult<Unit> = try {
        accountDao.upsertAll(api.getAccounts().map { it.toEntity() })
        LedgerResult.Success(Unit)
    } catch (t: Throwable) {
        LedgerResult.Failure(LedgerError.Network(t.message))
    }
}
