package xyz.hihasan.ledgerlite.uiautomator

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Automator test for the biometric prompt shown on Login. UI Automator is used here because the
 * system BiometricPrompt dialog is drawn outside the app process and is invisible to Espresso /
 * Compose testing.
 *
 * Run with `:app:connectedDebugAndroidTest`. Prep the emulator first:
 * ```
 * adb -e emu finger touch 1            # after the prompt appears
 * # or enrol a fingerprint under Settings > Security
 * ```
 * Bodies are intentionally empty.
 */
@RunWith(AndroidJUnit4::class)
class BiometricPromptFlowTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun tapping_use_biometrics_opens_the_system_prompt() { TODO() }

    @Test
    fun a_successful_fingerprint_authenticates_and_shows_the_dashboard() { TODO() }

    @Test
    fun cancelling_the_prompt_keeps_the_user_on_login() { TODO() }
}
