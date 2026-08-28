plugins {
    alias(libs.plugins.ledgerlite.jvm.library)
}

dependencies {
    api(project(":core:model"))
    api(project(":core:common"))
    api(libs.androidx.paging.common)
    implementation(libs.javax.inject)
}
