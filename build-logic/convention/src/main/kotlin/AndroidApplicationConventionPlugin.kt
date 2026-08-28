import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.hihasan.ledgerlite.LedgerSdk
import xyz.hihasan.ledgerlite.configureKotlinAndroid

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9's new DSL requires built-in Kotlin; the kotlin-android plugin must NOT be applied.
        pluginManager.apply("com.android.application")

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            defaultConfig {
                targetSdk = LedgerSdk.TARGET
                versionCode = 1
                versionName = "1.0"
            }
            buildFeatures {
                buildConfig = true
            }
        }

        configurations.configureEach {
            resolutionStrategy.force("androidx.concurrent:concurrent-futures:1.2.0")
        }
    }
}
