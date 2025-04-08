package com.digrec.hodl.feature.home.data

import com.digrec.hodl.feature.home.domain.getPlatform

/**
 * Created by Dejan Igrec
 */
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
