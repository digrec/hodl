package com.digrec.hodl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Created by Dejan Igrec
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hodl",
    ) {
        App()
    }
}
