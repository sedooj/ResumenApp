pluginManagement {
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
}

rootProject.name = "Resumen"
include(":app")
include(":feature:arch")
include(":feature:localstorage")
include(":shared:ui-kit")
include(":feature:app-ui")
include(":shared:api")
