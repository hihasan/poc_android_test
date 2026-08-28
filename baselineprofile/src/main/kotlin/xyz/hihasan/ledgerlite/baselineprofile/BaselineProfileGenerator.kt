package xyz.hihasan.ledgerlite.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Generates `app/src/main/baseline-prof.txt` — the classes/methods to AOT-compile for a faster
 * cold start and smoother first scroll.
 *
 * Generate (needs a rooted emulator or userdebug device):
 * ```
 * ./gradlew :app:generateBaselineProfile
 * ```
 * That runs this class via the `androidx.baselineprofile` plugin wired into `:app`.
 *
 * TODO: implement `baselineProfileRule.collect(packageName = TARGET_PACKAGE) { ... }` — walk the
 * critical journey (startup → dashboard → open transaction list → scroll → add expense).
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() { TODO() }

    private companion object {
        const val TARGET_PACKAGE = "xyz.hihasan.ledgerlite"
    }
}
