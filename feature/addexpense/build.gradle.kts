plugins {
    alias(libs.plugins.ledgerlite.android.feature)
    alias(libs.plugins.screenshot)
}

android {
    namespace = "xyz.hihasan.ledgerlite.feature.addexpense"

    @Suppress("UnstableApiUsage")
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    screenshotTestImplementation(platform(libs.androidx.compose.bom))
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
}

// The screenshot plugin's discovery of @Preview composables is still flaky on this AGP 9
// preview toolchain; don't hard-fail the build while the stubs are empty.
tasks.withType<Test>().configureEach {
    filter.isFailOnNoMatchingTests = false
    failOnNoDiscoveredTests = false
}
