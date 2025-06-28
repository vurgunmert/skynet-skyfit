import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.file.DuplicatesStrategy

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("com.google.gms.google-services")
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
            export("io.github.mirzemehdi:kmpnotifier:1.5.1")
            baseName = "composeApp"
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.vurgun.fiwe.composeApp")
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
            implementation(projects.core.data)
            implementation(projects.core.ui)
            implementation(projects.core.navigation)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(projects.feature.auth)
            implementation(projects.feature.onboarding)
            implementation(projects.feature.home)
            implementation(projects.feature.profile)
            implementation(projects.feature.explore)
            implementation(projects.feature.main)
            implementation(projects.feature.schedule)
            implementation(projects.feature.settings)
            implementation(projects.feature.health)
            implementation(projects.feature.connect)

            implementation("io.github.mirzemehdi:kmpnotifier:1.5.1")
        }

        androidMain.dependencies {
            implementation("androidx.startup:startup-runtime:1.2.0")
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
    namespace = "com.vurgun.fiwe"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.vurgun.fiwe"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
    packaging {
        resources {
            excludes += "/M ETA-INF/{AL2.0,LGPL2.1}"
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
//endregion Project: Android

compose.desktop {
    application {
        mainClass = "com.vurgun.fiwe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FIWE"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("src/desktopMain/resources/icons/fiwe.icns"))
            }
            windows {
                iconFile.set(project.file("src/desktopMain/resources/icons/fiwe.ico"))
                menuGroup = "Vurgun Teknoloji"
            }
            linux {
                iconFile.set(project.file("src/desktopMain/resources/icons/fiwe.png"))
            }

            modules("jdk.unsupported")
        }
    }
}

tasks.withType<ProcessResources>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
