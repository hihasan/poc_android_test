package xyz.hihasan.ledgerlite.feature.dashboard

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/** Local unit test for [DashboardViewModel]. Run with `:feature:dashboard:testDebugUnitTest`. */
class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `starts in Loading`() { TODO() }

    @Test
    fun `emits Ready with combined dashboard data`() { TODO() }

    @Test
    fun `emits Error when the use case flow throws`() { TODO() }
}
