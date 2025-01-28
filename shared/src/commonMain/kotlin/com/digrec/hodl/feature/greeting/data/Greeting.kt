package com.digrec.hodl.feature.greeting.data

import com.digrec.hodl.feature.greeting.domain.getPlatform

/**
 * Created by Dejan Igrec
 */
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
