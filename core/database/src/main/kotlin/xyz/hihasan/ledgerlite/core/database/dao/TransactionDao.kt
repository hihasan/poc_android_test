package xyz.hihasan.ledgerlite.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.database.entity.CategoryTotal
import xyz.hihasan.ledgerlite.core.database.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun pagingSourceNewestFirst(): PagingSource<Int, TransactionEntity>

    /**
     * Filtered + sorted paging query. Pass `null` / empty values to skip a facet. `sort`:
     * 0 = newest, 1 = oldest, 2 = amount desc, 3 = amount asc.
     */
    @Query(
        """
        SELECT * FROM transactions
        WHERE (:query = '' OR description LIKE '%' || :query || '%' OR note LIKE '%' || :query || '%')
          AND (:filterTypes = 0 OR type IN (:types))
          AND (:filterCategories = 0 OR category IN (:categories))
          AND (:minAmount IS NULL OR amountMinorUnits >= :minAmount)
          AND (:maxAmount IS NULL OR amountMinorUnits <= :maxAmount)
          AND (:from IS NULL OR timestamp >= :from)
          AND (:to IS NULL OR timestamp <= :to)
        ORDER BY
          CASE WHEN :sort = 0 THEN timestamp END DESC,
          CASE WHEN :sort = 1 THEN timestamp END ASC,
          CASE WHEN :sort = 2 THEN amountMinorUnits END DESC,
          CASE WHEN :sort = 3 THEN amountMinorUnits END ASC
        """,
    )
    fun pagingSourceFiltered(
        query: String,
        filterTypes: Int,
        types: List<String>,
        filterCategories: Int,
        categories: List<String>,
        minAmount: Long?,
        maxAmount: Long?,
        from: Long?,
        to: Long?,
        sort: Int,
    ): PagingSource<Int, TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<TransactionEntity?>

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<TransactionEntity>>

    @Query("SELECT COUNT(*) FROM transactions")
    fun count(): Flow<Int>

    @Query(
        """
        SELECT category AS category,
               SUM(amountMinorUnits) AS totalMinorUnits,
               COUNT(*) AS txCount
        FROM transactions
        WHERE type = :type AND timestamp BETWEEN :from AND :to
        GROUP BY category
        """,
    )
    fun categoryTotals(type: String, from: Long, to: Long): Flow<List<CategoryTotal>>

    @Query("SELECT COALESCE(SUM(amountMinorUnits), 0) FROM transactions WHERE type = :type AND timestamp BETWEEN :from AND :to")
    fun totalByType(type: String, from: Long, to: Long): Flow<Long>

    @Upsert
    suspend fun upsert(transaction: TransactionEntity)

    @Upsert
    suspend fun upsertAll(transactions: List<TransactionEntity>)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM transactions")
    suspend fun clear()
}
