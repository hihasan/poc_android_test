package xyz.hihasan.ledgerlite.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerButton
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews
import xyz.hihasan.ledgerlite.core.domain.repository.AppSettings

@Composable
fun SettingsRoute(
    onLoggedOut: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val seeding by viewModel.seeding.collectAsStateWithLifecycle()

    SettingsContent(
        settings = settings,
        seeding = seeding,
        onDarkThemeChange = viewModel::onDarkThemeChange,
        onBiometricChange = viewModel::onBiometricChange,
        onSeed = { viewModel.seed() },
        onLogout = { viewModel.onLogout(onLoggedOut) },
    )
}

/** Stateless Settings screen. [SettingsRoute] owns the ViewModel and the logout callback. */
@Composable
fun SettingsContent(
    settings: AppSettings,
    seeding: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onBiometricChange: (Boolean) -> Unit,
    onSeed: () -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag(LedgerTestTags.SETTINGS_SCREEN),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SettingRow(
            label = "Dark theme",
            checked = settings.darkTheme,
            onCheckedChange = onDarkThemeChange,
            testTag = LedgerTestTags.SETTINGS_DARK_THEME_SWITCH,
        )
        SettingRow(
            label = "Unlock with biometrics",
            checked = settings.biometricUnlock,
            onCheckedChange = onBiometricChange,
            testTag = LedgerTestTags.SETTINGS_BIOMETRIC_SWITCH,
        )
        Text("Default currency: ${settings.defaultCurrency}")

        LedgerButton(
            text = if (seeding) "Generating 10k transactions…" else "Generate demo data (10k)",
            onClick = onSeed,
            enabled = !seeding,
            modifier = Modifier.testTag(LedgerTestTags.SETTINGS_SEED_DATA_BUTTON),
        )
        LedgerButton(
            text = "Log out",
            onClick = onLogout,
            modifier = Modifier.testTag(LedgerTestTags.SETTINGS_LOGOUT_BUTTON),
        )
    }
}

@Composable
private fun SettingRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag),
        )
    }
}

// --- Previews ------------------------------------------------------------------

@ThemePreviews
@Composable
private fun SettingsContentPreview() = LedgerTheme {
    SettingsContent(
        settings = AppSettings(darkTheme = false, biometricUnlock = true, defaultCurrency = "USD"),
        seeding = false,
        onDarkThemeChange = {},
        onBiometricChange = {},
        onSeed = {},
        onLogout = {},
    )
}

@ThemePreviews
@Composable
private fun SettingsContentSeedingPreview() = LedgerTheme {
    SettingsContent(
        settings = AppSettings(darkTheme = true, biometricUnlock = false, defaultCurrency = "EUR"),
        seeding = true,
        onDarkThemeChange = {},
        onBiometricChange = {},
        onSeed = {},
        onLogout = {},
    )
}

@ThemePreviews
@Composable
private fun SettingRowPreview() = LedgerTheme {
    Surface {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SettingRow(label = "Dark theme", checked = true, onCheckedChange = {}, testTag = "")
            SettingRow(label = "Unlock with biometrics", checked = false, onCheckedChange = {}, testTag = "")
        }
    }
}
