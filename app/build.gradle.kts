plugins {
    alias(libs.plugins.ledgerlite.android.application)
    alias(libs.plugins.ledgerlite.android.compose)
    alias(libs.plugins.ledgerlite.android.hilt)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "xyz.hihasan.ledgerlite"

    defaultConfig {
        applicationId = "xyz.hihasan.ledgerlite"
        testInstrumentationRunner = "xyz.hihasan.ledgerlite.core.testing.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            proguardFiles("benchmark-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:notifications"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:transactions"))
    implementation(project(":feature:addexpense"))
    implementation(project(":feature:search"))
    implementation(project(":feature:settings"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.profileinstaller)

    baselineProfile(project(":baselineprofile"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.hilt.android.testing)
}
