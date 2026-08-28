package xyz.hihasan.ledgerlite.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * A tightened take on the Material 3 type scale. Headlines lean on heavier weights and slightly
 * negative tracking for a crisp, editorial feel; labels are spaced out a touch so buttons and
 * eyebrow captions read as deliberate. Body styles are left at their defaults.
 */
private val Default = Typography()

val LedgerTypography: Typography = Typography(
    displayLarge = Default.displayLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = (-1).sp),
    displayMedium = Default.displayMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    displaySmall = Default.displaySmall.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineLarge = Default.headlineLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    headlineMedium = Default.headlineMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.25).sp),
    headlineSmall = Default.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
    titleLarge = Default.titleLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = (-0.25).sp),
    titleMedium = Default.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = Default.labelLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = 0.3.sp),
    labelMedium = Default.labelMedium.copy(fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp),
    labelSmall = Default.labelSmall.copy(fontWeight = FontWeight.Medium, letterSpacing = 1.sp),
)
