import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import xyz.hihasan.ledgerlite.libs

/**
 * Convention for a `:feature:*` module: Android library + Compose + Hilt + navigation + the
 * shared core modules every screen needs. Screens only add their own feature deps on top.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("ledgerlite.android.library")
            apply("ledgerlite.android.compose")
            apply("ledgerlite.android.hilt")
        }

        extensions.configure<LibraryExtension> {
            defaultConfig.testInstrumentationRunner =
                "xyz.hihasan.ledgerlite.core.testing.HiltTestRunner"
        }

        dependencies {
            add("implementation", project(":core:model"))
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:designsystem"))
            add("implementation", project(":core:common"))

            add("implementation", libs.findLibrary("androidx-navigation-compose").get())
            add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
            add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())

            add("testImplementation", project(":core:testing"))
            add("androidTestImplementation", project(":core:testing"))
        }
    }
}
