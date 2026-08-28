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
import com.google.common.truth.Truth.assertThat
import xyz.hihasan.ledgerlite.MainActivity
import javax.inject.Inject

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
 * The [MockWebServer] is owned by `:core:testing` `FakeNetworkModule`, which replaces
 * `NetworkUrlModule` via `@TestInstallIn` for every `@HiltAndroidTest`. Inject it here and swap its
 * `dispatcher` to script per-scenario responses; it already serves
 * `xyz.hihasan.ledgerlite.core.testing.network.MockApiDispatcher`'s happy-path canned responses.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginToDashboardE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        // Sanity-check the test backend is up before driving the UI.
        assertThat(mockWebServer.port).isGreaterThan(0)
    }

    @After
    fun tearDown() {
        // The singleton MockWebServer is torn down with the test process; nothing to close here.
    }

    @Test
    fun login_then_add_expense_then_see_the_dashboard_balance_update() { TODO() }

    @Test
    fun a_failed_login_keeps_the_user_on_the_login_screen() { TODO() }
}
