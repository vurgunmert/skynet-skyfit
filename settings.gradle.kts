rootProject.name = "SkyFit"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")

include(":core:analytics")
include(":core:network")
include(":core:data")
include(":core:ui")
include(":core:navigation")
include(":core:utils")

include(":feature:auth")
include(":feature:onboarding")
include(":feature:main")
include(":feature:home")
include(":feature:profile")
include(":feature:settings")
include(":feature:schedule")
include(":feature:connect")
include(":feature:explore")
include(":feature:health")