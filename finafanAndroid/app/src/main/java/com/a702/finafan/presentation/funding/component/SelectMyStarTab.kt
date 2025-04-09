package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.gradientBlue
import com.a702.finafan.domain.funding.model.MyStar

@Composable
fun MyStarRow(
    myStars: List<MyStar>,
    selectedStar: MyStar?,
    onStarSelected: (MyStar) -> Unit,
    modifier: Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        items(myStars) { star ->
            MyStarItem(
                star = star,
                isSelected = star == selectedStar,
                onClick = { onStarSelected(star) }
            )
        }
    }
}

@Composable
fun MyStarItem(
    star: MyStar,
    isSelected: Boolean,
    onClick: () -> Unit
){
    val painter = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
        .data(star.imageUrl)
        .build()
)
    val borderColor: Brush = if (isSelected) gradientBlue else  SolidColor(MainWhite)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            Image(
                painter = painter,
                contentDescription = "Star Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(color = MainWhite)
                    .align(Alignment.Center)
                    .border(3.dp, brush = borderColor, shape = CircleShape)
                    .clickable { onClick() }
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = star.name,
            fontSize = 16.sp
        )
    }
}