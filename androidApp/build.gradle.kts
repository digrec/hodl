plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvmToolchain(jdkVersion = 17)

    dependencies {
        implementation(projects.shared)
        implementation(libs.androidx.activityCompose)
        implementation(libs.koin.android)
        implementation(libs.androidx.splashScreen)
        debugImplementation(libs.compose.uiTooling)
        debugImplementation(libs.compose.uiToolingPreview)
    }
}

android {
    namespace = libs.versions.packageName.get()
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.packageName.get()

        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        versionCode = calculateVersionCode(libs.versions.versionName.get(), logger)
        versionName = libs.versions.versionName.get()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
