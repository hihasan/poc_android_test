plugins {
    alias(libs.plugins.ledgerlite.jvm.library)
}

dependencies {
    api(libs.javax.inject)
    api(libs.kotlinx.coroutines.core)
}
