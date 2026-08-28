package xyz.hihasan.ledgerlite.feature.settings

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for Settings. Tags: [LedgerTestTags.SETTINGS_DARK_THEME_SWITCH],
 * [LedgerTestTags.SETTINGS_BIOMETRIC_SWITCH], [LedgerTestTags.SETTINGS_SEED_DATA_BUTTON],
 * [LedgerTestTags.SETTINGS_LOGOUT_BUTTON].
 */
class SettingsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun toggling_dark_theme_calls_the_view_model() { TODO() }

    @Test
    fun seed_button_shows_progress_text_while_seeding() { TODO() }

    @Test
    fun logout_button_invokes_onLoggedOut() { TODO() }
}
