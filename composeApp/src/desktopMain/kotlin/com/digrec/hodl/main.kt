package com.digrec.hodl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.digrec.hodl.di.initKoin
import org.koin.core.logger.Level

/**
 * Created by Dejan Igrec
 */
fun main() = application {

    initKoin {
        printLogger(Level.DEBUG)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Hodl",
    ) {
        HodlApp()
    }
}
