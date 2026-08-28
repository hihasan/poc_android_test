package xyz.hihasan.ledgerlite.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun SettingsRoute(
    onLoggedOut: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val seeding by viewModel.seeding.collectAsStateWithLifecycle()

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
            onCheckedChange = viewModel::onDarkThemeChange,
            testTag = LedgerTestTags.SETTINGS_DARK_THEME_SWITCH,
        )
        SettingRow(
            label = "Unlock with biometrics",
            checked = settings.biometricUnlock,
            onCheckedChange = viewModel::onBiometricChange,
            testTag = LedgerTestTags.SETTINGS_BIOMETRIC_SWITCH,
        )
        Text("Default currency: ${settings.defaultCurrency}")

        LedgerButton(
            text = if (seeding) "Generating 10k transactions…" else "Generate demo data (10k)",
            onClick = { viewModel.seed() },
            enabled = !seeding,
            modifier = Modifier.testTag(LedgerTestTags.SETTINGS_SEED_DATA_BUTTON),
        )
        LedgerButton(
            text = "Log out",
            onClick = { viewModel.onLogout(onLoggedOut) },
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
