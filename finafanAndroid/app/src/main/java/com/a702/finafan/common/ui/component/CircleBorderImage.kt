package com.a702.finafan.common.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.a702.finafan.common.ui.theme.MainTextGray

@Composable
fun CircleBorderImage(modifier: Modifier = Modifier, imageUrl: String) {

    AsyncImage(
        model = imageUrl,
        contentDescription = "",
        modifier = modifier
            .clip(CircleShape)
            .border(1.5.dp, color = MainTextGray, shape = CircleShape),
        contentScale = ContentScale.Crop
    )

}