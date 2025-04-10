import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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
            baseName = "UiCore"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.calf.filepicker)
            implementation(libs.calf.permissions)

            implementation(projects.data.core)
            implementation(projects.data.courses)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.material3)
            api(compose.material3AdaptiveNavigationSuite)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            implementation(compose.components.resources)

            api(libs.kotlinx.datetime) //TODO: probably goes to data:core

            implementation(libs.coil.compose)
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
    namespace = "com.vurgun.skyfit.ui.core"
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