package com.digrec.hodl.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Stable
@ConsistentCopyVisibility
data class AppColors internal constructor(
    val btcOrange: Color,
    val materialColors: ColorScheme,
)

@Composable
fun appColors(): AppColors = remember {
    darkColorScheme()
}
