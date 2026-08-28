package xyz.hihasan.ledgerlite.feature.auth

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for the Register screen. Tags: [LedgerTestTags.REGISTER_SCREEN],
 * [LedgerTestTags.REGISTER_EMAIL_FIELD], [LedgerTestTags.REGISTER_CONFIRM_PASSWORD_FIELD],
 * [LedgerTestTags.REGISTER_SUBMIT_BUTTON].
 */
class RegisterScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun mismatched_passwords_show_a_field_error() { TODO() }

    @Test
    fun blank_name_shows_a_field_error() { TODO() }

    @Test
    fun successful_registration_triggers_onAuthenticated() { TODO() }
}
