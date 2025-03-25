package com.a702.finafan.common.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val gradientList = listOf(

    Brush.radialGradient
        (colors = listOf(Color(0xFF00C5DF), Color(0xFF907CFF), Color(0xFF18A0FB)),
        radius = 4000f),
    Brush.radialGradient(
        colors = listOf(Color(0xFF18A0FB), Color(0xFF1BC47D), Color(0xFF00C5DF)),
        radius = 4000f),
    Brush.radialGradient(
        colors = listOf(Color(0xFF18A0FB), Color(0xFF907CFF), Color(0xFF18A0FB)),
        radius = 4000f),
    Brush.radialGradient(
        colors = listOf(Color(0xFF00C5DF), Color(0xFF18A0FB), Color(0xFF907CFF)),
        radius = 4000f),
)

val gradientBlue = Brush.horizontalGradient(
    colors = listOf(MainGradBlue, MainGradViolet)
)