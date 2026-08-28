import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import xyz.hihasan.ledgerlite.libs

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.devtools.ksp")
            apply("com.google.dagger.hilt.android")
        }

        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("ksp", libs.findLibrary("hilt-compiler").get())

            // Doubles for instrumented Hilt tests.
            add("androidTestImplementation", libs.findLibrary("hilt-android-testing").get())
            add("kspAndroidTest", libs.findLibrary("hilt-compiler").get())
            // Doubles for Robolectric-style local Hilt tests (optional, kept available).
            add("testImplementation", libs.findLibrary("hilt-android-testing").get())
            add("kspTest", libs.findLibrary("hilt-compiler").get())
        }
    }
}
