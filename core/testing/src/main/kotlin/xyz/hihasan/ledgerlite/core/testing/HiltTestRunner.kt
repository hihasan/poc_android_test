package xyz.hihasan.ledgerlite.core.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Instrumentation runner that swaps in [HiltTestApplication] so `@HiltAndroidTest` classes get a
 * test component. Wire it up per module:
 *
 * ```
 * android { defaultConfig { testInstrumentationRunner = "xyz.hihasan.ledgerlite.core.testing.HiltTestRunner" } }
 * ```
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application =
        super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
