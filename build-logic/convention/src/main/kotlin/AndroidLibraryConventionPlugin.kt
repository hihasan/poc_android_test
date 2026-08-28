import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import xyz.hihasan.ledgerlite.configureKotlinAndroid
import xyz.hihasan.ledgerlite.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9's new DSL requires built-in Kotlin; the kotlin-android plugin must NOT be applied.
        pluginManager.apply("com.android.library")

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }

        // `androidx.test:core` pins concurrent-futures to `strictly 1.1.0`; hilt-android-testing
        // and newer AndroidX want 1.2.0. Force it so instrumented test classpaths resolve.
        configurations.configureEach {
            resolutionStrategy.force("androidx.concurrent:concurrent-futures:1.2.0")
        }

        dependencies {
            add("implementation", libs.findLibrary("androidx-core-ktx").get())
            add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())

            add("testImplementation", libs.findLibrary("junit4").get())
            add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            add("testImplementation", libs.findLibrary("turbine").get())
            add("testImplementation", libs.findLibrary("truth").get())
            add("testImplementation", libs.findLibrary("mockk").get())

            add("androidTestImplementation", libs.findLibrary("androidx-test-core").get())
            add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
            add("androidTestImplementation", libs.findLibrary("androidx-test-rules").get())
            add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
            add("androidTestImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            add("androidTestImplementation", libs.findLibrary("turbine").get())
            add("androidTestImplementation", libs.findLibrary("truth").get())
        }
    }
}
