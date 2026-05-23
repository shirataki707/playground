pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "playground"
include(":app")

file("core")
    .takeIf { it.isDirectory }
    ?.listFiles { f -> f.isDirectory && f.resolve("build.gradle.kts").exists() }
    ?.sortedBy { it.name }
    ?.forEach { include(":core:${it.name}") }

file("feature")
    .takeIf { it.isDirectory }
    ?.listFiles()
    ?.filter { it.isDirectory }
    ?.sortedBy { it.name }
    ?.forEach { featureDir ->
        featureDir.listFiles { f -> f.isDirectory && f.resolve("build.gradle.kts").exists() }
            ?.sortedBy { it.name }
            ?.forEach { subDir -> include(":feature:${featureDir.name}:${subDir.name}") }
    }
