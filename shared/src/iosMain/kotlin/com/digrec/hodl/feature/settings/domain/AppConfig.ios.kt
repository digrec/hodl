package com.digrec.hodl.feature.settings.domain

import platform.Foundation.NSBundle

/**
 * Created by Dejan Igrec
 */
class IOSAppConfig : AppConfig {
    override val version: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
            ?: "Unknown"
}
