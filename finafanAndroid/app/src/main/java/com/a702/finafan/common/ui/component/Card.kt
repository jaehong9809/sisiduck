package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun Card(
    content: String,
    modifier: Modifier){
    Box(
        modifier
            .width(320.dp)
            .heightIn()
            .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp))
            .background(MainWhite)
            .padding(20.dp)
    ) {
        Text(content)
    }
}