plugins {
    alias(libs.plugins.ledgerlite.android.library)
    alias(libs.plugins.ledgerlite.android.hilt)
}

android {
    namespace = "xyz.hihasan.ledgerlite.core.data"
}

dependencies {
    api(project(":core:model"))
    api(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.androidx.paging.testing)

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.paging.testing)
}
