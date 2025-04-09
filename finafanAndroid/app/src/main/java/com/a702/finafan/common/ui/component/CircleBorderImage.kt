package com.a702.finafan.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.MainBlack

@Composable
fun CircleBorderImage(modifier: Modifier = Modifier, imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier
            .clip(CircleShape)
//            .background(LightGray)
            .border(1.dp, color = MainBlack, shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}