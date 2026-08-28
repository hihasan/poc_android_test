package xyz.hihasan.ledgerlite

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val JAVA_VERSION = JavaVersion.VERSION_11
private val JVM_TARGET = JvmTarget.JVM_11

/**
 * Common `android { }` configuration shared by application and library modules.
 * AGP 9's new DSL supplies built-in Kotlin, so we configure Kotlin compiler options on the
 * compile tasks rather than through the (absent) `kotlin { }` extension.
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension) {
    commonExtension.compileSdk = LedgerSdk.COMPILE
    commonExtension.defaultConfig.minSdk = LedgerSdk.MIN
    commonExtension.compileOptions.sourceCompatibility = JAVA_VERSION
    commonExtension.compileOptions.targetCompatibility = JAVA_VERSION

    tasks.withType(KotlinCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(JVM_TARGET)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}

/** Configuration for pure-JVM (non-Android) modules such as `:core:model` and `:core:domain`. */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }
    extensions.configure<KotlinJvmProjectExtension> {
        compilerOptions {
            jvmTarget.set(JVM_TARGET)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}
