package xyz.hihasan.ledgerlite.feature.auth

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Thin wrapper around [BiometricPrompt]. The host Activity must be a [FragmentActivity]
 * (MainActivity is). Used for UI Automator testing of the biometric flow on login.
 */
class BiometricPromptManager(private val activity: FragmentActivity) {

    fun canAuthenticate(): Boolean =
        BiometricManager.from(activity).canAuthenticate(ALLOWED_AUTHENTICATORS) ==
            BiometricManager.BIOMETRIC_SUCCESS

    fun prompt(
        title: String = "Unlock LedgerLite",
        subtitle: String = "Confirm it's you to continue",
        onSuccess: () -> Unit,
        onError: (CharSequence) -> Unit = {},
        onFailed: () -> Unit = {},
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) =
                    onSuccess()

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) =
                    onError(errString)

                override fun onAuthenticationFailed() = onFailed()
            },
        )
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText("Use password")
            .setAllowedAuthenticators(ALLOWED_AUTHENTICATORS)
            .build()
        biometricPrompt.authenticate(info)
    }

    private companion object {
        const val ALLOWED_AUTHENTICATORS =
            BiometricManager.Authenticators.BIOMETRIC_WEAK or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
    }
}
