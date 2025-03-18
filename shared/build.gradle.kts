plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activityCompose)
            api(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.navigation)
            implementation(libs.kermit)
            api(libs.koin)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeViewmodel)
        }

        val desktopMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "${libs.versions.packageName.get()}.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// Updates iOS version during the resource generation phase
tasks.named("generateComposeResClass") {
    dependsOn("updateIosVersion")
}

tasks.register<UpdateIosVersion>("updateIosVersion") {
    val vName = libs.versions.versionName.get()
    versionName.set(vName)
    versionCode.set(calculateVersionCode(vName, logger))
}
