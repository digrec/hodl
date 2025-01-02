package com.digrec.hodl

import platform.UIKit.UIDevice

/**
 * Created by Dejan Igrec
 */
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
