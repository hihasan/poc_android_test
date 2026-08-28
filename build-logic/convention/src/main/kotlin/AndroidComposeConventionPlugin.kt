import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.hihasan.ledgerlite.configureAndroidCompose

/**
 * Applies the Kotlin Compose compiler plugin and wires the shared Compose dependency set.
 * Apply this on top of either the application or library convention plugin.
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        when {
            pluginManager.hasPlugin("com.android.application") ->
                extensions.configure<ApplicationExtension> { configureAndroidCompose(this) }

            pluginManager.hasPlugin("com.android.library") ->
                extensions.configure<LibraryExtension> { configureAndroidCompose(this) }

            else -> error("ledgerlite.android.compose requires the android application or library plugin")
        }
    }
}
