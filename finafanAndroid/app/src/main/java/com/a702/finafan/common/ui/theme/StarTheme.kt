package com.a702.finafan.common.ui.theme
import androidx.compose.ui.graphics.Color

data class StarTheme(
    val start: Color,
    val end: Color,
    val mid: Color
)

val starThemes = listOf(
    StarTheme(
        start = starGradGreen,
        end = starGradTurquoise,
        mid = starMidGreen
    ),
    StarTheme(
        start = starGradPink,
        end = starGradSkyBlue,
        mid = starMidViolet
    ),
    StarTheme(
        start = starGradYellow,
        end = starGradOrange,
        mid = starMidOrange
    )
)