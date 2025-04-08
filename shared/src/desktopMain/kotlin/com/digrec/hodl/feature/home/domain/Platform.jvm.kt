package com.digrec.hodl.feature.home.domain

/**
 * Created by Dejan Igrec
 */
class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
