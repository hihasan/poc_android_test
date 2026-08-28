package xyz.hihasan.ledgerlite.macrobenchmark

import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Scroll-performance Macrobenchmark for the paginated Transaction List. Seed 10k+ rows first
 * (Settings → "Generate demo data"), navigate to the list, then fling.
 *
 * Run: `./gradlew :macrobenchmark:connectedBenchmarkAndroidTest`.
 *
 * TODO: implement with `FrameTimingMetric()` + a UI Automator fling loop over the list
 * (`By.res("xyz.hihasan.ledgerlite", "transaction_list")`).
 */
@RunWith(AndroidJUnit4::class)
class TransactionListScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollTransactionListCompilationNone() { TODO() }

    @Test
    fun scrollTransactionListCompilationBaselineProfile() { TODO() }

    private companion object {
        const val TARGET_PACKAGE = "xyz.hihasan.ledgerlite"
        const val LIST_TAG = "transaction_list"
    }
}
