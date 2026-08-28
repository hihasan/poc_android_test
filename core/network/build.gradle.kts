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

    defaultConfig {
        // Real endpoint the app talks to. Instrumented tests replace this seam with an in-process
        // MockWebServer via `:core:testing` `FakeNetworkModule` (@TestInstallIn on NetworkUrlModule).
        buildConfigField("String", "API_BASE_URL", "\"https://api.ledgerlite.example/\"")
    }

    buildTypes {
        getByName("debug") {
            // 10.0.2.2 = host loopback from the emulator; point a local backend here while developing.
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8080/\"")
        }
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
}
