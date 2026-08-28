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
            onValueChange = viewModel::onEmailChange,
            label = "Email",
            keyboardType = KeyboardType.Email,
            errorText = state.fieldErrors["email"],
            modifier = Modifier.testTag(LedgerTestTags.LOGIN_EMAIL_FIELD),
        )
        LedgerTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
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
            onClick = viewModel::submitLogin,
            enabled = !state.isSubmitting,
            modifier = Modifier.testTag(LedgerTestTags.LOGIN_SUBMIT_BUTTON),
        )
        if (state.biometricAvailable) {
            LedgerButton(
                text = "Use biometrics",
                onClick = {
                    biometricManager?.prompt(onSuccess = viewModel::onBiometricAuthSucceeded)
                },
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
            onValueChange = viewModel::onDisplayNameChange,
            label = "Name",
            errorText = state.fieldErrors["displayName"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_NAME_FIELD),
        )
        LedgerTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = "Email",
            keyboardType = KeyboardType.Email,
            errorText = state.fieldErrors["email"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_EMAIL_FIELD),
        )
        LedgerTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            isPassword = true,
            errorText = state.fieldErrors["password"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_PASSWORD_FIELD),
        )
        LedgerTextField(
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = "Confirm password",
            isPassword = true,
            errorText = state.fieldErrors["confirmPassword"],
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_CONFIRM_PASSWORD_FIELD),
        )
        state.generalError?.let { Text(it) }
        LedgerButton(
            text = if (state.isSubmitting) "Creating…" else "Create account",
            onClick = viewModel::submitRegister,
            enabled = !state.isSubmitting,
            modifier = Modifier.testTag(LedgerTestTags.REGISTER_SUBMIT_BUTTON),
        )
        TextButton(onClick = onBack) { Text("Back to sign in") }
    }
}
