package xyz.hihasan.ledgerlite.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.hihasan.ledgerlite.core.database.dao.AccountDao

@RunWith(AndroidJUnit4::class)
class AccountDaoTest {

    private lateinit var db: LedgerDatabase
    private lateinit var dao: AccountDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LedgerDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = db.accountDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun upsertAll_then_observeAll_returns_sorted_accounts() { TODO() }

    @Test
    fun observeTotalBalance_sums_balances() { TODO() }
}
