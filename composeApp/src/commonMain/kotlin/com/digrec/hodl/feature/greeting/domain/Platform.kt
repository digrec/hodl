package com.digrec.hodl.feature.greeting.domain

/**
 * Created by Dejan Igrec
 */
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
