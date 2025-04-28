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

include(":core:network")
include(":core:data")
include(":core:ui")
include(":core:navigation")

include(":data:bodyanalysis")
include(":data:calendar")
include(":data:courses")
include(":data:messaging")
include(":data:onboarding")
include(":data:settings")

include(":feature:splash")
include(":feature:auth")
include(":feature:onboarding")
include(":feature:dashboard")
include(":feature:calendar")
include(":feature:settings")
include(":feature:courses")
include(":feature:notification")
include(":feature:home")
include(":feature:social")
include(":feature:profile")
include(":feature:nutrition")
include(":feature:messaging")
include(":feature:explore")
include(":feature:exercise")
include(":feature:bodyanalysis")
include(":feature:appointments")