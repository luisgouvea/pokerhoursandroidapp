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

rootProject.name = "baseandroidapp"
include(":app")
include(":core")
include(":core:data")
include(":core:network")
include(":core:common")
include(":core:model")
include(":core:domain")
include(":core:designsystem")
include(":feature:user")
