package com.digrec.hodl

import androidx.compose.runtime.Composable
import com.digrec.hodl.feature.greeting.ui.GreetingScreen
import com.digrec.hodl.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

/**
 * Created by Dejan Igrec
 */
@Composable
@Preview
fun HodlApp() {
    KoinContext {
        AppTheme {
            GreetingScreen()
        }
    }
}
