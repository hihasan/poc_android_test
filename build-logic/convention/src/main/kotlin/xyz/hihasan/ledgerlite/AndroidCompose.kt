package xyz.hihasan.ledgerlite

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** Enables Compose and adds the baseline Compose dependencies + tooling. */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension) {
    commonExtension.buildFeatures.compose = true

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())
        add("implementation", libs.findLibrary("androidx-compose-material-icons-extended").get())
        add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
        add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())

        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())

        add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())

        // Present only when the Compose Preview Screenshot Testing plugin is applied.
        configurations.findByName("screenshotTestImplementation")?.let {
            add("screenshotTestImplementation", platform(bom))
            add("screenshotTestImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }
    }
}
