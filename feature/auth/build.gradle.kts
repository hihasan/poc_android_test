plugins {
    alias(libs.plugins.ledgerlite.android.feature)
}

android {
    namespace = "xyz.hihasan.ledgerlite.feature.auth"
}

dependencies {
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.fragment.ktx)
}
