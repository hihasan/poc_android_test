package xyz.hihasan.ledgerlite.e2e

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import xyz.hihasan.ledgerlite.MainActivity

/**
 * END-TO-END test: full `login → add expense → dashboard updates` journey through the real
 * NavHost, real ViewModels, real Room, and Retrofit pointed at an in-process [MockWebServer].
 *
 * This is the only test that exercises every layer together. Keep the count low; prefer the
 * per-screen Compose tests and use-case unit tests for detail coverage.
 *
 * Run with `:app:connectedDebugAndroidTest`. Uses [xyz.hihasan.ledgerlite.core.testing.HiltTestRunner]
 * (configured as the module's `testInstrumentationRunner`).
 *
 * TODO: to fully control the backend, replace `NetworkModule` with a `@TestInstallIn` module that
 * provides a `MockWebServer` you own here (see TESTING.md → "Swapping test doubles").
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginToDashboardE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    // Provided by the app's NetworkModule today; swap for a test-owned instance via @TestInstallIn.
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        // TODO: obtain / start the MockWebServer and script its Dispatcher.
    }

    @After
    fun tearDown() {
        // TODO: shut the MockWebServer down if this test owns it.
    }

    @Test
    fun login_then_add_expense_then_see_the_dashboard_balance_update() { TODO() }

    @Test
    fun a_failed_login_keeps_the_user_on_the_login_screen() { TODO() }
}
