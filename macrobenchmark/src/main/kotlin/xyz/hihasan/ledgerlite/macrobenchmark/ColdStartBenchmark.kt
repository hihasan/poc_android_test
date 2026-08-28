package xyz.hihasan.ledgerlite.macrobenchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Cold-start Macrobenchmark for LedgerLite.
 *
 * Run on a physical device (emulator numbers are noisy) with:
 * ```
 * ./gradlew :macrobenchmark:connectedBenchmarkAndroidTest
 * ```
 * `TARGET_PACKAGE` is the app under test; the `benchmark` build type is release-like + profileable.
 *
 * TODO: fill in `measureRepeated(...) { pressHome(); startActivityAndWait() }`.
 */
@RunWith(AndroidJUnit4::class)
class ColdStartBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun coldStartNoCompilation() { TODO() }

    @Test
    fun coldStartWithBaselineProfile() { TODO() }

    private companion object {
        const val TARGET_PACKAGE = "xyz.hihasan.ledgerlite"
        val STARTUP_MODE = StartupMode.COLD
    }
}
