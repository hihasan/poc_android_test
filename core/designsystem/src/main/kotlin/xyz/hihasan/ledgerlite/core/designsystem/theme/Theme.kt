package xyz.hihasan.ledgerlite.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * LedgerLite is deliberately monochrome. The palette is pure black, pure white, and a ramp of
 * neutral greys — no hue is ever introduced, not for accents and not for errors. Every screen
 * pulls its colours from [MaterialTheme.colorScheme], so this file is the single place the whole
 * app's look is defined.
 */

// Neutral ramp ---------------------------------------------------------------
private val Black = Color(0xFF000000)
private val White = Color(0xFFFFFFFF)
private val Grey99 = Color(0xFFFCFCFC)
private val Grey97 = Color(0xFFF6F6F6)
private val Grey94 = Color(0xFFF0F0F0)
private val Grey90 = Color(0xFFE5E5E5)
private val Grey84 = Color(0xFFD6D6D6)
private val Grey70 = Color(0xFFB3B3B3)
private val Grey58 = Color(0xFF949494)
private val Grey45 = Color(0xFF737373)
private val Grey32 = Color(0xFF525252)
private val Grey22 = Color(0xFF383838)
private val Grey16 = Color(0xFF292929)
private val Grey12 = Color(0xFF1F1F1F)
private val Grey08 = Color(0xFF141414)

private val LightColors = lightColorScheme(
    primary = Black,
    onPrimary = White,
    primaryContainer = Grey16,
    onPrimaryContainer = White,
    secondary = Grey32,
    onSecondary = White,
    secondaryContainer = Grey90,
    onSecondaryContainer = Grey16,
    tertiary = Grey32,
    onTertiary = White,
    tertiaryContainer = Grey90,
    onTertiaryContainer = Grey16,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = Grey94,
    onSurfaceVariant = Grey45,
    surfaceContainerLowest = White,
    surfaceContainerLow = Grey99,
    surfaceContainer = Grey97,
    surfaceContainerHigh = Grey94,
    surfaceContainerHighest = Grey90,
    surfaceTint = Black,
    inverseSurface = Grey12,
    inverseOnSurface = Grey94,
    inversePrimary = White,
    outline = Grey58,
    outlineVariant = Grey90,
    error = Grey16,
    onError = White,
    errorContainer = Grey90,
    onErrorContainer = Black,
    scrim = Black,
)

private val DarkColors = darkColorScheme(
    primary = White,
    onPrimary = Black,
    primaryContainer = Grey84,
    onPrimaryContainer = Black,
    secondary = Grey70,
    onSecondary = Black,
    secondaryContainer = Grey16,
    onSecondaryContainer = Grey90,
    tertiary = Grey70,
    onTertiary = Black,
    tertiaryContainer = Grey16,
    onTertiaryContainer = Grey90,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    surfaceVariant = Grey16,
    onSurfaceVariant = Grey70,
    surfaceContainerLowest = Black,
    surfaceContainerLow = Grey08,
    surfaceContainer = Grey12,
    surfaceContainerHigh = Grey16,
    surfaceContainerHighest = Grey22,
    surfaceTint = White,
    inverseSurface = Grey90,
    inverseOnSurface = Grey16,
    inversePrimary = Black,
    outline = Grey45,
    outlineVariant = Grey22,
    error = Grey84,
    onError = Black,
    errorContainer = Grey22,
    onErrorContainer = White,
    scrim = Black,
)

@Composable
fun LedgerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = LedgerTypography,
        shapes = LedgerShapes,
        content = content,
    )
}
