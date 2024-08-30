buildscript {
    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.kotlin.serialization.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.verison.catalog.update.plugin)
}

versionCatalogUpdate {
    keep {
        versions.set(listOf("compose", "kotlin"))
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(false)
        keepUnusedPlugins.set(false)
    }
}