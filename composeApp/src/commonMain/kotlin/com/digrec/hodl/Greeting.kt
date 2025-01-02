package com.digrec.hodl

/**
 * Created by Dejan Igrec
 */
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
