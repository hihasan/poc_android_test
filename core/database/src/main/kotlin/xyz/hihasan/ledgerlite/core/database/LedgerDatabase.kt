package xyz.hihasan.ledgerlite.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.hihasan.ledgerlite.core.database.dao.AccountDao
import xyz.hihasan.ledgerlite.core.database.dao.TransactionDao
import xyz.hihasan.ledgerlite.core.database.entity.AccountEntity
import xyz.hihasan.ledgerlite.core.database.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, AccountEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class LedgerDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao

    companion object {
        const val NAME = "ledger.db"
    }
}
