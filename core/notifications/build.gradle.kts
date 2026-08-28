plugins {
    alias(libs.plugins.ledgerlite.android.library)
    alias(libs.plugins.ledgerlite.android.hilt)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.notifications"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
}
