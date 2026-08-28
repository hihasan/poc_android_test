plugins {
    alias(libs.plugins.ledgerlite.android.library)
    alias(libs.plugins.ledgerlite.android.hilt)
    alias(libs.plugins.ledgerlite.android.room)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.database"
}

dependencies {
    api(project(":core:model"))
    implementation(libs.androidx.paging.runtime)
}
