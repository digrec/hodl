package com.digrec.hodl

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.digrec.hodl.navigation.NavigationScaffold
import com.digrec.hodl.ui.theme.App
import com.digrec.hodl.ui.theme.AppTheme

/**
 * Created by Dejan Igrec
 */
@Composable
fun HodlApp() {
    val navHostController = rememberNavController()
    AppTheme {
        NavigationScaffold(
            navHostController = navHostController,
            modifier = Modifier.background(color = App.colors.materialColors.background),
        )
    }
}
