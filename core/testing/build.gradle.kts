plugins {
    alias(libs.plugins.ledgerlite.android.library)
    // Runs the Hilt/KSP processor over this main source set so `FakeNetworkModule`'s
    // `@TestInstallIn` aggregation metadata is generated for consuming `@HiltAndroidTest` runs.
    alias(libs.plugins.ledgerlite.android.hilt)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.testing"
}

// This module's *main* source set holds shared test infrastructure so it can be consumed as
// `testImplementation` / `androidTestImplementation` by every other module.
dependencies {
    api(project(":core:model"))
    api(project(":core:domain"))
    api(project(":core:data"))
    api(project(":core:database"))
    api(project(":core:network"))

    api(libs.hilt.android)
    api(libs.hilt.android.testing)

    api(libs.androidx.test.core)
    api(libs.androidx.test.core.ktx)
    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.ext.junit)
    api(libs.androidx.room.testing)
    api(libs.androidx.paging.testing)
    api(libs.okhttp.mockwebserver)

    api(libs.kotlinx.coroutines.test)
    api(libs.junit4)
    api(libs.turbine)
    api(libs.truth)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui.test.junit4)
}
