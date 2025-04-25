package com.digrec.hodl

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
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
        title = "Hodl",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            width = 1000.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center),
        ),
    ) {
        HodlApp()
    }
}
