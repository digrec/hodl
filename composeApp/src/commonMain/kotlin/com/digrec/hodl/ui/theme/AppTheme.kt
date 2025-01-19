@file:Suppress("MatchingDeclarationName")

package com.digrec.hodl.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.digrec.hodl.ui.composition.LocalAppColors
import com.digrec.hodl.ui.composition.LocalAppTypographies
import com.digrec.hodl.ui.theme.color.AppColors
import com.digrec.hodl.ui.theme.color.appColors
import com.digrec.hodl.ui.theme.typography.AppTypographies
import com.digrec.hodl.ui.theme.typography.appTypographies

object App {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typographies: AppTypographies
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypographies.current
}

@Composable
fun AppTheme(
    colors: AppColors = appColors(),
    typographies: AppTypographies = appTypographies(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypographies provides typographies,
    ) {
        MaterialTheme(
            colorScheme = colors.materialColors,
            typography = typographies.material,
            content = content,
        )
    }
}
