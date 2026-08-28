package xyz.hihasan.ledgerlite.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.usecase.LoginUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.ObserveAuthSessionUseCase
import xyz.hihasan.ledgerlite.core.domain.usecase.RegisterUseCase
import javax.inject.Inject

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val displayName: String = "",
    val fieldErrors: Map<String, String> = emptyMap(),
    val generalError: String? = null,
    val isSubmitting: Boolean = false,
    val isAuthenticated: Boolean = false,
    val biometricAvailable: Boolean = false,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val register: RegisterUseCase,
    observeSession: ObserveAuthSessionUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeSession().collect { session ->
                _state.update { it.copy(isAuthenticated = session != null) }
            }
        }
    }

    fun onEmailChange(value: String) = _state.update { it.copy(email = value) }
    fun onPasswordChange(value: String) = _state.update { it.copy(password = value) }
    fun onConfirmPasswordChange(value: String) = _state.update { it.copy(confirmPassword = value) }
    fun onDisplayNameChange(value: String) = _state.update { it.copy(displayName = value) }
    fun setBiometricAvailable(available: Boolean) =
        _state.update { it.copy(biometricAvailable = available) }

    fun submitLogin() = submit {
        login(_state.value.email, _state.value.password)
    }

    fun submitRegister() = submit {
        register(
            email = _state.value.email,
            password = _state.value.password,
            confirmPassword = _state.value.confirmPassword,
            displayName = _state.value.displayName,
        )
    }

    /** Called after a successful biometric unlock; reuses the stored credentials path. */
    fun onBiometricAuthSucceeded() = submit { login(_state.value.email, _state.value.password) }

    private fun submit(block: suspend () -> LedgerResult<*>) {
        _state.update { it.copy(isSubmitting = true, generalError = null, fieldErrors = emptyMap()) }
        viewModelScope.launch {
            when (val result = block()) {
                is LedgerResult.Success -> _state.update { it.copy(isSubmitting = false) }
                is LedgerResult.Failure -> _state.update {
                    when (val error = result.error) {
                        is LedgerError.Validation ->
                            it.copy(isSubmitting = false, fieldErrors = error.fieldErrors)
                        else ->
                            it.copy(isSubmitting = false, generalError = error.message ?: "Something went wrong")
                    }
                }
            }
        }
    }
}
