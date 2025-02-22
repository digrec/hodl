package com.digrec.hodl

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.digrec.hodl.navigation.Navigation
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
        val navHostController = rememberNavController()
        AppTheme {
            Navigation(navHostController = navHostController)
        }
    }
}
