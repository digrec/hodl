package com.digrec.hodl.ui.theme.typography

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypographies(
    val headline: TextStyle,
    val title: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
    val material: Typography,
)

@Composable
fun appTypographies(): AppTypographies {
    val materialTypography = Typography()

    return AppTypographies(
        headline = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 22.sp,
            lineHeight = 24.sp,
        ),
        title = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
            fontSize = 16.sp,
            lineHeight = 18.sp,
        ),
        body = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            fontSize = 12.sp,
            lineHeight = 14.sp,
        ),
        label = materialTypography.labelLarge.copy(fontSize = 12.sp),
        material = materialTypography,
    )
}
