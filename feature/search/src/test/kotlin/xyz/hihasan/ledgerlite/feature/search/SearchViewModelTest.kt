package xyz.hihasan.ledgerlite.feature.search

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/** Local unit test for [SearchViewModel]. Run with `:feature:search:testDebugUnitTest`. */
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onQueryChange updates the filter`() { TODO() }

    @Test
    fun `toggleType adds then removes a type`() { TODO() }

    @Test
    fun `results debounce filter changes`() { TODO() }

    @Test
    fun `clearFilters keeps the query but drops facets`() { TODO() }
}
