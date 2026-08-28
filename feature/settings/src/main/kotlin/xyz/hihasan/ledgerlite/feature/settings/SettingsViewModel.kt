package xyz.hihasan.ledgerlite.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.hihasan.ledgerlite.core.domain.repository.AppSettings
import xyz.hihasan.ledgerlite.core.domain.usecase.LogoutUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.ObserveSettingsUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.SeedTransactionsUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.SetBiometricUnlockUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.SetDarkThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSettings: ObserveSettingsUseCase,
    private val setDarkTheme: SetDarkThemeUseCase,
    private val setBiometricUnlock: SetBiometricUnlockUseCase,
    private val seedTransactions: SeedTransactionsUseCase,
    private val logout: LogoutUseCase,
) : ViewModel() {

    val settings: StateFlow<AppSettings> = observeSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    private val _seeding = MutableStateFlow(false)
    val seeding: StateFlow<Boolean> = _seeding.asStateFlow()

    fun onDarkThemeChange(enabled: Boolean) = viewModelScope.launch { setDarkTheme(enabled) }

    fun onBiometricChange(enabled: Boolean) = viewModelScope.launch { setBiometricUnlock(enabled) }

    fun seed(count: Int = 10_000) {
        _seeding.value = true
        viewModelScope.launch {
            seedTransactions(count)
            _seeding.value = false
        }
    }

    fun onLogout(onDone: () -> Unit) = viewModelScope.launch {
        logout()
        onDone()
    }
}
