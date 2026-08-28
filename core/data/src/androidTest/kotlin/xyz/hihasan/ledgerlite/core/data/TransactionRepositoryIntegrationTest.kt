package xyz.hihasan.ledgerlite.core.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.hihasan.ledgerlite.core.testing.database.TestDatabaseFactory
import xyz.hihasan.ledgerlite.core.testing.rules.MockWebServerRule

/**
 * Repository integration test: real Room (in-memory) + real Retrofit pointed at [MockWebServerRule].
 * Run with `:core:data:connectedDebugAndroidTest`.
 *
 * TODO: build `TransactionRepositoryImpl(dao, accountDao, api)` in [setUp] where `api` is a
 * Retrofit `LedgerApi` built against `mockWebServer.baseUrl`, then assert on the flows.
 */
@RunWith(AndroidJUnit4::class)
class TransactionRepositoryIntegrationTest {

    @get:Rule
    val mockWebServer = MockWebServerRule()

    private val db = TestDatabaseFactory.create()

    @Before
    fun setUp() {
        // TODO: construct the repository + Retrofit api here.
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addTransaction_persists_to_room_and_posts_to_the_api() { TODO() }

    @Test
    fun pagedTransactions_emits_inserted_rows_newest_first() { TODO() }

    @Test
    fun seed_inserts_the_requested_number_of_rows() { TODO() }

    @Test
    fun refresh_maps_a_network_error_to_a_Failure() { TODO() }
}
