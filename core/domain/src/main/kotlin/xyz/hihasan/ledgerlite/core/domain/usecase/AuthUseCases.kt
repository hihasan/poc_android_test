package xyz.hihasan.ledgerlite.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.repository.AuthRepository
import xyz.hihasan.ledgerlite.core.model.AuthSession
import javax.inject.Inject

private val EMAIL_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
private const val MIN_PASSWORD = 8

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): LedgerResult<AuthSession> {
        val errors = buildMap {
            if (!EMAIL_REGEX.matches(email.trim())) put("email", "Enter a valid email")
            if (password.length < MIN_PASSWORD) put("password", "Password must be at least $MIN_PASSWORD characters")
        }
        if (errors.isNotEmpty()) return LedgerResult.Failure(LedgerError.Validation(errors))
        return repository.login(email.trim(), password)
    }
}

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        displayName: String,
    ): LedgerResult<AuthSession> {
        val errors = buildMap {
            if (!EMAIL_REGEX.matches(email.trim())) put("email", "Enter a valid email")
            if (password.length < MIN_PASSWORD) put("password", "Password must be at least $MIN_PASSWORD characters")
            if (password != confirmPassword) put("confirmPassword", "Passwords do not match")
            if (displayName.isBlank()) put("displayName", "Name is required")
        }
        if (errors.isNotEmpty()) return LedgerResult.Failure(LedgerError.Validation(errors))
        return repository.register(email.trim(), password, displayName.trim())
    }
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.logout()
}

class ObserveAuthSessionUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(): Flow<AuthSession?> = repository.session
}
