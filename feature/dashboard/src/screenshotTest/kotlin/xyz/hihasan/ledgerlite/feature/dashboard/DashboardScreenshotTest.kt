package xyz.hihasan.ledgerlite.feature.dashboard

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import xyz.hihasan.ledgerlite.core.designsystem.theme.LedgerTheme

/**
 * Compose Preview Screenshot tests for the Dashboard.
 *
 * How this framework works: each non-private `@Preview @Composable` below IS a screenshot test.
 *   * `:feature:dashboard:updateDebugScreenshotTest`   — record reference PNGs
 *   * `:feature:dashboard:validateDebugScreenshotTest` — diff against them (CI)
 * References land in `src/debug/screenshotTest/reference/`. There are no assertions to write —
 * you curate the previews.
 *
 * TODO: replace the placeholder `Text` with a stateless `DashboardContent(...)` fed fixed data so
 * the image is deterministic. Keep the light + dark variants.
 */
@Preview(name = "Dashboard · light", showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun DashboardLightSnapshot() {
    LedgerTheme(darkTheme = false) {
        Text("TODO: DashboardContent(state = Ready(sampleDashboardData()))")
    }
}

@Preview(
    name = "Dashboard · dark",
    showBackground = true,
    widthDp = 400,
    heightDp = 800,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun DashboardDarkSnapshot() {
    LedgerTheme(darkTheme = true) {
        Text("TODO: DashboardContent(state = Ready(sampleDashboardData()))")
    }
}
