package xyz.hihasan.ledgerlite

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.navigation.LedgerNavHost

/**
 * Single-activity host. Extends [FragmentActivity] because `androidx.biometric.BiometricPrompt`
 * requires it.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LedgerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LedgerNavHost()
                }
            }
        }
    }
}
