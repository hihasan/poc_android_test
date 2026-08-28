package xyz.hihasan.ledgerlite.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.domain.repository.AppSettings
import xyz.hihasan.ledgerlite.core.domain.repository.SettingsRepository
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<AppSettings> = repository.settings
}

class SetDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(enabled: Boolean) = repository.setDarkTheme(enabled)
}

class SetBiometricUnlockUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(enabled: Boolean) = repository.setBiometricUnlock(enabled)
}

class SetDefaultCurrencyUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(code: String) = repository.setDefaultCurrency(code)
}
