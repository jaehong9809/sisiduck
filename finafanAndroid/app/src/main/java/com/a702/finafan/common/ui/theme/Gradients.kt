package com.a702.finafan.common.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradientList = listOf(

    Brush.linearGradient(
        colors = listOf(
            Color(0xFF907CFF),
            Color(0xFF18A0FB),
            Color(0xFF00C5DF)
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, 1000f)
    ),
    Brush.linearGradient(
        colors = listOf(
            Color(0xFF5A2A6E),
            Color(0xFF18A0FB),
            Color(0xFF00C5DF)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 2000f)
    ),
    Brush.linearGradient(
        colors = listOf(
            Color(0xFFA3E5FF),
            Color(0xFF86D8B7)
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, 1200f)
    ),
    Brush.linearGradient(
        colors = listOf(
            Color(0xFF002733),
            Color(0xFF1069B0)
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, 1200f)
    )

)

val gradientBlue = Brush.horizontalGradient(
    colors = listOf(MainGradBlue, MainGradViolet)
)