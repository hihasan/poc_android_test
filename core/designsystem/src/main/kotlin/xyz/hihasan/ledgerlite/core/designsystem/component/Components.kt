package xyz.hihasan.ledgerlite.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions

/** Primary button; callers pass a [Modifier.testTag] so tests can find it. */
@Composable
fun LedgerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(onClick = onClick, enabled = enabled, modifier = modifier.fillMaxWidth()) {
        Text(text)
    }
}

@Composable
fun LedgerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorText: String? = null,
    errorTestTag: String? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = errorText != null,
            visualTransformation =
                if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
        )
        if (errorText != null) {
            Text(
                text = errorText,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .then(if (errorTestTag != null) Modifier.testTag(errorTestTag) else Modifier),
            )
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message)
    }
}

/** Minimal bar chart used on the Dashboard. Values are rendered as proportional rows. */
@Composable
fun SpendingBarChart(
    entries: List<Pair<String, Float>>,
    modifier: Modifier = Modifier,
) {
    val max = (entries.maxOfOrNull { it.second } ?: 1f).coerceAtLeast(1f)
    Column(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        entries.forEach { (label, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(label)
                Text("%.0f%%".format(value / max * 100))
            }
        }
    }
}
