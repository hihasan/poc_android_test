package xyz.hihasan.ledgerlite.feature.dashboard

import android.content.res.Configuration
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
 * These render the real stateless [DashboardContent] with the shared [sampleDashboardData]
 * fixture so the images are deterministic.
 */
@Preview(name = "Dashboard · ready · light", showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun DashboardReadyLightSnapshot() {
    LedgerTheme(darkTheme = false) {
        DashboardContent(DashboardUiState.Ready(sampleDashboardData()), onAddTransaction = {})
    }
}

@Preview(
    name = "Dashboard · ready · dark",
    showBackground = true,
    widthDp = 400,
    heightDp = 800,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun DashboardReadyDarkSnapshot() {
    LedgerTheme(darkTheme = true) {
        DashboardContent(DashboardUiState.Ready(sampleDashboardData()), onAddTransaction = {})
    }
}

@Preview(name = "Dashboard · loading", showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun DashboardLoadingSnapshot() {
    LedgerTheme(darkTheme = false) {
        DashboardContent(DashboardUiState.Loading, onAddTransaction = {})
    }
}

@Preview(name = "Dashboard · error", showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun DashboardErrorSnapshot() {
    LedgerTheme(darkTheme = false) {
        DashboardContent(DashboardUiState.Error("Failed to load dashboard"), onAddTransaction = {})
    }
}
