package com.digrec.hodl.feature.greeting.domain

import android.os.Build

/**
 * Created by Dejan Igrec
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
