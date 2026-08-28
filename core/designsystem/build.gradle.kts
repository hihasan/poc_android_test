plugins {
    alias(libs.plugins.ledgerlite.android.library)
    alias(libs.plugins.ledgerlite.android.compose)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.designsystem"
}

dependencies {
    api(project(":core:model"))
}
