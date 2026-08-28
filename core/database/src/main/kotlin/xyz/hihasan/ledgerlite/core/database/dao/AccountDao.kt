package xyz.hihasan.ledgerlite.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.database.entity.AccountEntity

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    fun observeAll(): Flow<List<AccountEntity>>

    @Query("SELECT COALESCE(SUM(balanceMinorUnits), 0) FROM accounts")
    fun observeTotalBalance(): Flow<Long>

    @Query("SELECT * FROM accounts WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): AccountEntity?

    @Upsert
    suspend fun upsertAll(accounts: List<AccountEntity>)

    @Query("DELETE FROM accounts")
    suspend fun clear()
}
