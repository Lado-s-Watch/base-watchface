enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
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
    versionCatalogs {
        create("libs") {
            files("../gradle/libs.versions.toml")
        }
    }
}

rootProject.name = "Watchface"
include(":mobile")
include(":wear")
include(":core:common")
include(":core:ui")
include(":core:preview")
include(":core:ui-wear")
