package xyz.hihasan.ledgerlite.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.hihasan.ledgerlite.core.database.LedgerDatabase
import xyz.hihasan.ledgerlite.core.database.dao.AccountDao
import xyz.hihasan.ledgerlite.core.database.dao.TransactionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLedgerDatabase(@ApplicationContext context: Context): LedgerDatabase =
        Room.databaseBuilder(context, LedgerDatabase::class.java, LedgerDatabase.NAME)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun provideTransactionDao(db: LedgerDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideAccountDao(db: LedgerDatabase): AccountDao = db.accountDao()
}
