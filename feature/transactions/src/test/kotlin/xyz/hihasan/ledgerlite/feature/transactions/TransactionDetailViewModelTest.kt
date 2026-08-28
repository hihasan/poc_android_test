package xyz.hihasan.ledgerlite.feature.transactions

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/**
 * Local unit test for [TransactionDetailViewModel]. Feed the id via a `SavedStateHandle` and a
 * fake [xyz.hihasan.ledgerlite.core.domain.usecase.GetTransactionDetailUseCase].
 */
class TransactionDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `reads the transaction id from SavedStateHandle`() { TODO() }

    @Test
    fun `emits the transaction detail`() { TODO() }

    @Test
    fun `delete invokes the use case and the onDeleted callback`() { TODO() }
}
