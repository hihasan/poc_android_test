plugins {
    alias(libs.plugins.ledgerlite.android.feature)
}

android {
    namespace = "xyz.hihasan.ledgerlite.feature.search"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    androidTestImplementation(libs.androidx.paging.testing)
}
