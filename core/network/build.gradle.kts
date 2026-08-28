plugins {
    alias(libs.plugins.ledgerlite.android.library)
    alias(libs.plugins.ledgerlite.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(project(":core:model"))

    api(platform(libs.okhttp.bom))
    api(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)

    api(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)

    // Shipped in main so the app can run against an in-process fake API "for now".
    api(libs.okhttp.mockwebserver)
}
