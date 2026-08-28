package xyz.hihasan.ledgerlite.core.designsystem.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation: renders an `@Composable` in light and dark. `uiMode = UI_MODE_NIGHT_YES`
 * flips `isSystemInDarkTheme()`, which [LedgerTheme] reads — so wrap preview bodies in `LedgerTheme`
 * and both variants come out themed correctly.
 *
 * Used by the design-system component previews and every feature's `*Content` previews.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
@Preview(name = "light", showBackground = true)
@Preview(name = "dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
annotation class ThemePreviews
