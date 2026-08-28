import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import xyz.hihasan.ledgerlite.configureKotlinJvm
import xyz.hihasan.ledgerlite.libs

/**
 * Pure-JVM module (`:core:model`, `:core:domain`). Unit tests here run on JUnit 5 (Jupiter)
 * so the project exercises both JUnit 4 (Android modules) and JUnit 5 (JVM modules).
 */
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
            apply("java-library")
        }

        configureKotlinJvm()

        tasks.withType<Test>().configureEach { useJUnitPlatform() }

        dependencies {
            add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())

            add("testImplementation", libs.findLibrary("junit-jupiter-api").get())
            add("testImplementation", libs.findLibrary("junit-jupiter-params").get())
            add("testRuntimeOnly", libs.findLibrary("junit-jupiter-engine").get())
            add("testRuntimeOnly", libs.findLibrary("junit-platform-launcher").get())
            add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            add("testImplementation", libs.findLibrary("turbine").get())
            add("testImplementation", libs.findLibrary("truth").get())
            add("testImplementation", libs.findLibrary("mockk").get())
        }
    }
}
