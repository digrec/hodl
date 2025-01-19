package com.digrec.hodl.ui.theme.color

import androidx.compose.material3.darkColorScheme

internal fun darkColorScheme(): AppColors = AppColors(
    btcOrange = AppColorPalette.orange,
    materialColors = darkColorScheme(
        primary = AppColorPalette.orange,
        secondary = AppColorPalette.blue,
        background = AppColorPalette.black,
        error = AppColorPalette.red,
    ),
)
