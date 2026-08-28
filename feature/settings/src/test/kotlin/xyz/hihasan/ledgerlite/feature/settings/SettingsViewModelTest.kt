package xyz.hihasan.ledgerlite.feature.settings

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/** Local unit test for [SettingsViewModel]. Run with `:feature:settings:testDebugUnitTest`. */
class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `settings stream reflects the repository`() { TODO() }

    @Test
    fun `onDarkThemeChange writes through the use case`() { TODO() }

    @Test
    fun `seed toggles the seeding flag around the call`() { TODO() }

    @Test
    fun `onLogout calls logout then the onDone callback`() { TODO() }
}
