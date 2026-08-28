package xyz.hihasan.ledgerlite.core.testing.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import xyz.hihasan.ledgerlite.core.database.LedgerDatabase

/**
 * Builds an in-memory [LedgerDatabase] for DAO / repository integration tests. `allowMainThreadQueries`
 * keeps simple tests terse; drop it when asserting on threading.
 */
object TestDatabaseFactory {
    fun create(
        context: Context = ApplicationProvider.getApplicationContext(),
    ): LedgerDatabase =
        Room.inMemoryDatabaseBuilder(context, LedgerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}
