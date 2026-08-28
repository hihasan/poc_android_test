package xyz.hihasan.ledgerlite.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.hihasan.ledgerlite.core.database.dao.TransactionDao

/**
 * Instrumented DAO test — runs on a device/emulator against a real (in-memory) SQLite instance.
 * Run with `:core:database:connectedDebugAndroidTest`.
 *
 * TODO: implement. The in-memory DB is wired up for you; assertions are yours to write.
 */
@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var db: LedgerDatabase
    private lateinit var dao: TransactionDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LedgerDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = db.transactionDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun upsert_then_observeById_returns_the_row() { TODO() }

    @Test
    fun pagingSourceNewestFirst_orders_by_timestamp_desc() { TODO() }

    @Test
    fun pagingSourceFiltered_applies_query_and_type_facets() { TODO() }

    @Test
    fun categoryTotals_groups_expense_sums_by_category() { TODO() }

    @Test
    fun deleteById_removes_only_the_target_row() { TODO() }
}
