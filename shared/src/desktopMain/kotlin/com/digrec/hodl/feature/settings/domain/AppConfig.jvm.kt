package com.digrec.hodl.feature.settings.domain

/**
 * Created by Dejan Igrec
 */
class DesktopAppConfig : AppConfig {
    override val version: String
        get() = System.getProperty("jpackage.app-version")
            ?: object {}.javaClass.getPackage().implementationVersion
            ?: "Unknown"
}
