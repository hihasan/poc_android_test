package xyz.hihasan.ledgerlite.feature.addexpense

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/** Local unit test for [AddTransactionViewModel]. Run with `:feature:addexpense:testDebugUnitTest`. */
class AddTransactionViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onAmountChange updates the form state`() { TODO() }

    @Test
    fun `validateOnly publishes field errors without saving`() { TODO() }

    @Test
    fun `save sets savedSuccessfully on success`() { TODO() }

    @Test
    fun `save maps a validation failure to field errors`() { TODO() }

    @Test
    fun `switching to TRANSFER reveals the counterparty requirement`() { TODO() }
}
