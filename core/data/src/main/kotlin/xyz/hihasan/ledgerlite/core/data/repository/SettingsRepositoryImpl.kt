package xyz.hihasan.ledgerlite.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.hihasan.ledgerlite.core.domain.repository.AppSettings
import xyz.hihasan.ledgerlite.core.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override val settings: Flow<AppSettings> = dataStore.data.map { prefs ->
        AppSettings(
            darkTheme = prefs[KEY_DARK] ?: false,
            biometricUnlock = prefs[KEY_BIOMETRIC] ?: false,
            defaultCurrency = prefs[KEY_CURRENCY] ?: "USD",
        )
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { it[KEY_DARK] = enabled }
    }

    override suspend fun setBiometricUnlock(enabled: Boolean) {
        dataStore.edit { it[KEY_BIOMETRIC] = enabled }
    }

    override suspend fun setDefaultCurrency(code: String) {
        dataStore.edit { it[KEY_CURRENCY] = code }
    }

    private companion object {
        val KEY_DARK = booleanPreferencesKey("settings_dark_theme")
        val KEY_BIOMETRIC = booleanPreferencesKey("settings_biometric_unlock")
        val KEY_CURRENCY = stringPreferencesKey("settings_default_currency")
    }
}
