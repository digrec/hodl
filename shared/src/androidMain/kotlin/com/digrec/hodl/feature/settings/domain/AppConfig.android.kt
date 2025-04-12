package com.digrec.hodl.feature.settings.domain

import android.content.Context

/**
 * Created by Dejan Igrec
 */
class AndroidAppConfig(private val context: Context) : AppConfig {
    override val version: String
        get() = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            ?: "Unknown"
}
