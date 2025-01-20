package com.digrec.hodl.feature.greeting.domain

/**
 * Created by Dejan Igrec
 */
class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
