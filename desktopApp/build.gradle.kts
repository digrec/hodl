import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "${libs.versions.packageName.get()}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            packageName = libs.versions.packageName.get()
            packageVersion = libs.versions.versionName.get()
        }
    }
}

composeCompiler {
    // Hot reload support
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
