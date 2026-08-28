package xyz.hihasan.ledgerlite.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme
import xyz.hihasan.ledgerlite.core.designsystem.theme.ThemePreviews

/**
 * IDE / screenshot previews for the shared design-system components. Each is wrapped in
 * [LedgerTheme] and rendered in both light and dark via [ThemePreviews].
 */
@ThemePreviews
@Composable
private fun LedgerButtonPreview() = LedgerTheme {
    Surface {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LedgerButton(text = "Sign in", onClick = {})
            LedgerButton(text = "Disabled", onClick = {}, enabled = false)
        }
    }
}

@ThemePreviews
@Composable
private fun LedgerTextFieldPreview() = LedgerTheme {
    Surface {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            LedgerTextField(
                value = "jane@doe.com",
                onValueChange = {},
                label = "Email",
                keyboardType = KeyboardType.Email,
            )
            LedgerTextField(
                value = "",
                onValueChange = {},
                label = "Password",
                isPassword = true,
                errorText = "Password is required",
            )
        }
    }
}

@ThemePreviews
@Composable
private fun LoadingIndicatorPreview() = LedgerTheme {
    Surface(Modifier.fillMaxWidth().height(160.dp)) {
        LoadingIndicator()
    }
}

@ThemePreviews
@Composable
private fun EmptyStatePreview() = LedgerTheme {
    Surface(Modifier.fillMaxWidth().height(160.dp)) {
        EmptyState(message = "No transactions yet")
    }
}

@ThemePreviews
@Composable
private fun SpendingBarChartPreview() = LedgerTheme {
    Surface {
        SpendingBarChart(
            entries = listOf(
                "GROCERIES" to 420f,
                "DINING" to 180f,
                "TRANSPORT" to 95f,
                "UTILITIES" to 60f,
            ),
        )
    }
}
