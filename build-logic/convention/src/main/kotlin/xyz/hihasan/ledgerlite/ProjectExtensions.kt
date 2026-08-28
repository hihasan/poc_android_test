package xyz.hihasan.ledgerlite

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/** Shared SDK levels for every Android module in the project. */
object LedgerSdk {
    const val COMPILE = 37
    const val MIN = 33
    const val TARGET = 36
}

/** Accessor for the `libs` version catalog from inside a convention plugin. */
val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
