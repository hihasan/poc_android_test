package xyz.hihasan.ledgerlite.feature.auth

import org.junit.Rule
import org.junit.Test
import xyz.hihasan.ledgerlite.core.testing.rules.MainDispatcherRule

/**
 * Local unit test (JVM, JUnit 4) for [AuthViewModel]. Run with `:feature:auth:testDebugUnitTest`.
 *
 * TODO: provide fakes for LoginUseCase / RegisterUseCase / ObserveAuthSessionUseCase, then
 * assert on [AuthViewModel.state]. Bodies are intentionally empty.
 */
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // private lateinit var viewModel: AuthViewModel

    @Test
    fun `submitLogin surfaces field errors from a validation failure`() { TODO() }

    @Test
    fun `submitLogin clears errors and stops submitting on success`() { TODO() }

    @Test
    fun `submitRegister maps a mismatched password to a field error`() { TODO() }

    @Test
    fun `session emission flips isAuthenticated`() { TODO() }

    @Test
    fun `setBiometricAvailable toggles the biometric button state`() { TODO() }
}
