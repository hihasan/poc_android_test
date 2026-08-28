package xyz.hihasan.ledgerlite.feature.search

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for Search / Filter. Tags: [LedgerTestTags.SEARCH_QUERY_FIELD],
 * [LedgerTestTags.SEARCH_RESULTS_LIST], type chips [LedgerTestTags.SEARCH_TYPE_CHIP_PREFIX] + name.
 */
class SearchScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun typing_a_query_filters_the_results() { TODO() }

    @Test
    fun toggling_a_type_chip_narrows_the_results() { TODO() }

    @Test
    fun tapping_a_result_invokes_onTransactionClick() { TODO() }
}
