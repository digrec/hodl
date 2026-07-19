import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvmToolchain(jdkVersion = 17)

    dependencies {
        implementation(projects.shared)
        implementation(compose.desktop.currentOs)
        implementation(libs.kotlinx.coroutinesSwing)
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
