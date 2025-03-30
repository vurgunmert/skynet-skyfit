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

    //region Target: Android
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    //endregion Target: Android

    //region Target: iOS
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
    //endregion Target: iOS

    //region Target: Desktop
    jvm("desktop")
    //endregion Target: Desktop

    //region Target: Web-IR
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }
    //endregion Target:  Web-IR

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            // Compose Multiplatform libraries
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)

            // Compose Components
//            implementation("com.google.accompanist:accompanist-permissions:0.37.2")

            // Kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.datetime)

            // Koin for dependency injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktor client core and content negotiation
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)


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

//            // FilePicker
            implementation(libs.calf.filepicker)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.calf.permissions)
            implementation(libs.androidx.ui.tooling.preview.android)

            implementation("app.rive:rive-android:9.6.5")
            implementation("androidx.startup:startup-runtime:1.2.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.calf.permissions)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.cio)
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.material)
                implementation(compose.html.core)
                implementation(libs.skiko.js)
                implementation(libs.ktor.client.js)
            }
        }
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