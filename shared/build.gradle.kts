@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    android {
        namespace = "${libs.versions.packageName.get()}.shared"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()

        minSdk = libs.versions.androidMinSdk.get().toInt()

        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    listOf(
        iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            optimized = buildType == NativeBuildType.RELEASE
            freeCompilerArgs += "-Xbinary=bundleId=com.digrec.hodl.SharedFramework"
        }
    }

    jvm(name = "desktop")

    jvmToolchain(jdkVersion = 17)

    dependencies {
        implementation(libs.compose.foundation)
        implementation(libs.compose.materialIconsExtended)
        implementation(libs.compose.material3)
        implementation(libs.compose.runtime)
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
        implementation(libs.compose.uiToolingPreview)
        implementation(libs.compose.material3AdaptiveLayout)
        implementation(libs.compose.material3AdaptiveNavigation)
        implementation(libs.compose.material3AdaptiveNavigationSuite)
        implementation(libs.compose.material3WindowSizeClass1)
        implementation(libs.androidx.navigation)
        implementation(libs.androidx.roomRuntime)
        implementation(libs.androidx.sqlite)
        implementation(libs.androidx.viewmodel)
        implementation(libs.compose.uiBackhandler)
        implementation(libs.kermit)
        api(libs.koin)
        implementation(libs.koin.compose)
        implementation(libs.koin.composeViewmodel)
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiTooling)
        }
        iosMain.dependencies {
            implementation(libs.kotlinx.coroutinesCore)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.roomCompiler)
    add("kspIosSimulatorArm64", libs.androidx.roomCompiler)
    add("kspIosArm64", libs.androidx.roomCompiler)
    add("kspDesktop", libs.androidx.roomCompiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

// Updates iOS version during the resource generation phase
tasks.named("generateComposeResClass") {
    dependsOn("updateIosVersion")
}

tasks.register<UpdateIosVersion>("updateIosVersion") {
    description = "Updates iOS version during the resource generation phase"
    val vName = libs.versions.versionName.get()
    versionName.set(vName)
    versionCode.set(calculateVersionCode(vName, logger))
}
