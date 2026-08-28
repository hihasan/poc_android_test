package xyz.hihasan.ledgerlite.feature.auth

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags

/**
 * Compose UI test for the Login screen. Run with `:feature:auth:connectedDebugAndroidTest`.
 *
 * Reference nodes by the tags in [LedgerTestTags], e.g.:
 * ```
 * composeRule.setContent { LedgerTheme { /* LoginRoute with a fake AuthViewModel */ } }
 * composeRule.onNodeWithTag(LedgerTestTags.LOGIN_EMAIL_FIELD).performTextInput("a@b.com")
 * composeRule.onNodeWithTag(LedgerTestTags.LOGIN_PASSWORD_FIELD).performTextInput("password1")
 * composeRule.onNodeWithTag(LedgerTestTags.LOGIN_SUBMIT_BUTTON).performClick()
 * composeRule.onNodeWithTag(LedgerTestTags.LOGIN_ERROR_TEXT).assertIsDisplayed()
 * ```
 * Bodies are intentionally empty.
 */
class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun shows_validation_error_for_a_bad_email() { TODO() }

    @Test
    fun submit_button_is_disabled_while_submitting() { TODO() }

    @Test
    fun biometric_button_is_shown_only_when_available() { TODO() }

    @Test
    fun tapping_create_account_navigates_to_register() { TODO() }
}
