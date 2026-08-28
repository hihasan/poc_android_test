pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LedgerLite"

include(":app")

include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:domain")
include(":core:database")
include(":core:network")
include(":core:data")
include(":core:notifications")
include(":core:testing")

include(":feature:auth")
include(":feature:dashboard")
include(":feature:transactions")
include(":feature:addexpense")
include(":feature:search")
include(":feature:settings")

include(":macrobenchmark")
include(":baselineprofile")
