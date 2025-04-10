import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.file.DuplicatesStrategy

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.vurgun.skyfit.composeApp")
            isStatic = true
        }
    }

    jvm("desktop")

//    js(IR) {
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.ui.core)

            implementation(projects.data.core)
            implementation(projects.data.network)
            implementation(projects.data.user)

            implementation(projects.feature.auth)
            implementation(projects.feature.onboarding)
            implementation(projects.feature.dashboard)
            implementation(projects.feature.settings)
            implementation(projects.feature.calendar)
            implementation(projects.feature.courses)
            implementation(projects.feature.notification)
            implementation(projects.feature.profile)
            implementation(projects.feature.social)
            implementation(projects.feature.messaging)
            implementation(projects.feature.home)
            implementation(projects.feature.explore)
            implementation(projects.feature.exercise)
            implementation(projects.feature.appointments)
            implementation(projects.feature.home)

            // Kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.datetime)

            // Koin for dependency injection
            implementation(libs.koin.core)
            implementation(libs.bundles.koin.compose)

            // peekaboo for Camera work
//            implementation(libs.peekaboo.ui)
//            implementation(libs.peekaboo.image.picker)

            // Coil for image loading
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
//            implementation("network.chaintech:compose-multiplatform-media-player:1.0.30")

            // PreCompose for multiplatform navigation
            implementation(libs.precompose)
            implementation(libs.precompose.koin)
        }

        androidMain.dependencies {
        }

        iosMain.dependencies {
        }

        desktopMain.dependencies {
        }

//        val jsMain by getting {
//            dependencies {
//                implementation(compose.foundation)
//                implementation(compose.animation)
//                implementation(compose.material)
//                implementation(compose.html.core)
//                implementation(libs.skiko.js)
//                implementation(libs.ktor.client.js)
//            }
//        }
    }
}

//region Project: Android
android {
    namespace = "com.vurgun.skyfit"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.vurgun.skyfit"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}
dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}
//endregion Project: Android

compose.desktop {
    application {
        mainClass = "com.vurgun.skyfit.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.vurgun.skyfit"
            packageVersion = "1.0.0"
        }
    }
}

tasks.withType<ProcessResources>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}