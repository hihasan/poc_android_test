plugins {
    alias(libs.plugins.ledgerlite.android.feature)
}

android {
    namespace = "xyz.hihasan.ledgerlite.feature.transactions"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    androidTestImplementation(libs.androidx.paging.testing)
}
