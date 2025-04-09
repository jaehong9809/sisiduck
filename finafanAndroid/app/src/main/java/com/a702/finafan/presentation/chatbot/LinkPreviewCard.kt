package com.a702.finafan.presentation.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.link.model.LinkPreviewMeta

@Composable
fun LinkPreviewCard(
    meta: LinkPreviewMeta
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MainWhite, RoundedCornerShape(12.dp))
            .clickable { uriHandler.openUri(meta.link) }
            .padding(12.dp)
    ) {
        if (meta.imageUrl.isNotBlank()) {
            AsyncImage(
                model = meta.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(8.dp))
        }

        Text(meta.title, fontSize = 16.sp, color = MainBlack)

        if (meta.description.isNotBlank()) {
            Spacer(Modifier.height(4.dp))
            Text(meta.description, fontSize = 14.sp, color = MainTextGray)
        }
    }
}