import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
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
            optimized = true
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
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
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

        iosMain.dependencies {
            implementation(libs.kotlinx.coroutinesCore)
        }

        val desktopMain by getting {
            dependencies {
                api(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)
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

composeCompiler {
    // Hot reload support
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
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
