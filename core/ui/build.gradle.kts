import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
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
            baseName = "CoreUi"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Core UI Module for SkyFit App"
        homepage = "https://your-link-to-CoreUi-homepage.com"
        version = "1.0"
        ios.deploymentTarget = "17.0" // ✅ Match your project setup!
        podfile = rootProject.file("iosApp/Podfile") // ✅ Correct, not relative path!

        framework {
            baseName = "CoreUi" // ✅ NOT ComposeApp anymore, it's CoreUi!
            isStatic = false
        }

        pod("RiveRuntime") {
            version = libs.versions.pods.rive.runtime.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }


    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.calf.filepicker)
            implementation(libs.calf.permissions)

            implementation(projects.core.data)
            implementation(projects.data.courses)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            implementation(compose.components.resources)

            api(libs.kotlinx.datetime) //TODO: probably goes to data:core

            //Navigation
            api(libs.bundles.voyager)

            implementation(libs.coil.compose)
            implementation(libs.coil.network)
        }

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)

            implementation("app.rive:rive-android:9.6.5")
            implementation("androidx.startup:startup-runtime:1.2.0")
        }

        desktopMain.dependencies {
            api(compose.desktop.currentOs)
            api(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.vurgun.skyfit.core.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
}

compose.resources {
    publicResClass = true
    generateResClass = always
}