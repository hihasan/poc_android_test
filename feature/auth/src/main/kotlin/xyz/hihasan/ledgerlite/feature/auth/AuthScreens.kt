package xyz.hihasan.ledgerlite.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerButton
import xyz.hihasan.ledgerlite.core.designsystem.component.LedgerTextField
import xyz.hihasan.ledgerlite.core.designsystem.testing.LedgerTestTags
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews

@Composable
fun LoginRoute(
    onAuthenticated: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = LocalContext.current as? FragmentActivity
    val biometricManager = remember(activity) { activity?.let { BiometricPromptManager(it) } }

    LaunchedEffect(biometricManager) {
        viewModel.setBiometricAvailable(biometricManager?.canAuthenticate() == true)
    }
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onAuthenticated()
    }

    LoginContent(
        state = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSubmit = viewModel::submitLogin,
        onUseBiometrics = {
            biometricManager?.prompt(onSuccess = viewModel::onBiometricAuthSucceeded)
        },
        onNavigateToRegister = onNavigateToRegister,
    )
}

/** Stateless Login form. [LoginRoute] owns the ViewModel, session/biometric effects, and nav. */
@Composable
fun LoginContent(
    state: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onUseBiometrics: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag(LedgerTestTags.LOGIN_SCREEN),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Welcome back")
        LedgerTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Email",
            keyboardType = KeyboardType.Email,
            errorText = state.fieldErrors["email"],
            modifier = Modifier.testTag(LedgerTestTags.LOGIN_EMAIL_FIELD),
        )
        LedgerTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Password",
            isPassword = true,
            errorText = state.fieldErrors["password"],
            modifier = Modifier.testTag(LedgerTestTags.LOGIN_PASSWORD_FIELD),
        )
        state.generalError?.let {
            Text(it, modifier = Modifier.testTag(LedgerTestTags.LOGIN_ERROR_TEXT))
        }
        LedgerButton(
            text = if (state.isSubmitting) "Signing in…" else "Sign in",
            onClick = onSubmit,
            enabled = !state.isSubmitting,
            modifier = Modifier.testTag(LedgerTestTags.LOGIN_SUBMIT_BUTTON),
        )
        if (state.biometricAvailable) {
            LedgerButton(
                text = "Use biometrics",
                onClick = onUseBiometrics,
                modifier = Modifier.testTag(LedgerTestTags.LOGIN_BIOMETRIC_BUTTON),
            )
        }
        TextButton(onClick = onNavigateToRegister) {
            Text("Create an account")
        }
    }
}

@Composable
fun RegisterRoute(
    onAuthenticated: () -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onAuthenticated()
    }

    RegisterContent(
        state = state,
        onDisplayNameChange = viewModel::onDisplayNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onSubmit = viewModel::submitRegister,
        onBack = onBack,
    )
}

/** Stateless Register form. [RegisterRoute] owns the ViewModel, the session effect, and nav. */
@Composable
fun RegisterContent(
    state: AuthUiState,
    onDisplayNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag(LedgerTestTags.REGISTER_SCREEN),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Create your account")
        LedgerTextField(
            value = state.displayName,
            onValueChange = onDisplayNameChange,
            label = "Name",
            errorText = state.fieldErrors["displayName"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_NAME_FIELD),
        )
        LedgerTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Email",
            keyboardType = KeyboardType.Email,
            errorText = state.fieldErrors["email"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_EMAIL_FIELD),
        )
        LedgerTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Password",
            isPassword = true,
            errorText = state.fieldErrors["password"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_PASSWORD_FIELD),
        )
        LedgerTextField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm password",
            isPassword = true,
            errorText = state.fieldErrors["confirmPassword"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_CONFIRM_PASSWORD_FIELD),
        )
        state.generalError?.let { Text(it) }
        LedgerButton(
            text = if (state.isSubmitting) "Creating…" else "Create account",
            onClick = onSubmit,
            enabled = !state.isSubmitting,
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_SUBMIT_BUTTON),
        )
        TextButton(onClick = onBack) { Text("Back to sign in") }
    }
}

// --- Previews ------------------------------------------------------------------

@ThemePreviews
@Composable
private fun LoginContentPreview() = LedgerTheme {
    LoginContent(
        state = AuthUiState(email = "jane@doe.com", password = "hunter2", biometricAvailable = true),
        onEmailChange = {},
        onPasswordChange = {},
        onSubmit = {},
        onUseBiometrics = {},
        onNavigateToRegister = {},
    )
}

@ThemePreviews
@Composable
private fun LoginContentErrorPreview() = LedgerTheme {
    LoginContent(
        state = AuthUiState(
            email = "jane@doe.com",
            generalError = "Invalid email or password",
            fieldErrors = mapOf("password" to "Password is required"),
        ),
        onEmailChange = {},
        onPasswordChange = {},
        onSubmit = {},
        onUseBiometrics = {},
        onNavigateToRegister = {},
    )
}

@ThemePreviews
@Composable
private fun RegisterContentPreview() = LedgerTheme {
    RegisterContent(
        state = AuthUiState(
            displayName = "Jane Doe",
            email = "jane@doe.com",
            password = "hunter2",
            confirmPassword = "hunter2",
        ),
        onDisplayNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onSubmit = {},
        onBack = {},
    )
}

@ThemePreviews
@Composable
private fun RegisterContentErrorPreview() = LedgerTheme {
    RegisterContent(
        state = AuthUiState(
            displayName = "Jane Doe",
            email = "not-an-email",
            fieldErrors = mapOf(
                "email" to "Enter a valid email",
                "confirmPassword" to "Passwords don't match",
            ),
        ),
        onDisplayNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onSubmit = {},
        onBack = {},
    )
}
