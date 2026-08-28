package xyz.hihasan.ledgerlite.feature.transactions

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/**
 * Local unit test for [TransactionListViewModel]. Use `androidx.paging:paging-testing`
 * (`asSnapshot { }`) to assert on the paged stream. Run with `:feature:transactions:testDebugUnitTest`.
 */
class TransactionListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `exposes the paged transactions from the use case`() { TODO() }

    @Test
    fun `caches paging state in viewModelScope`() { TODO() }
}
