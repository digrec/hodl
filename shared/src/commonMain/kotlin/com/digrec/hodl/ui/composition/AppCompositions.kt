@file:Suppress("CompositionLocalAllowlist")

package com.digrec.hodl.ui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.digrec.hodl.ui.theme.color.AppColors
import com.digrec.hodl.ui.theme.typography.AppTypographies

internal val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("${AppColors::class.simpleName} needs to be set")
}
internal val LocalAppTypographies = staticCompositionLocalOf<AppTypographies> {
    error("${AppTypographies::class.simpleName} needs to be set")
}
