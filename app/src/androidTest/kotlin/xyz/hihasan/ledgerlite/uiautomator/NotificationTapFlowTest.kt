package xyz.hihasan.ledgerlite.uiautomator

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Automator test for the "transaction added" notification and its deep link
 * (`ledgerlite://transaction/{id}` → Transaction Detail). The notification shade belongs to
 * SystemUI, so UI Automator (not Compose testing) is required to open it and tap the notification.
 *
 * Run with `:app:connectedDebugAndroidTest`.
 */
@RunWith(AndroidJUnit4::class)
class NotificationTapFlowTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // TODO: grant POST_NOTIFICATIONS, add an expense, then device.openNotification()
    }

    @Test
    fun adding_an_expense_posts_a_notification() { TODO() }

    @Test
    fun tapping_the_notification_opens_transaction_detail_for_that_id() { TODO() }

    @Test
    fun cold_start_via_the_notification_deep_link_lands_on_detail() { TODO() }
}
