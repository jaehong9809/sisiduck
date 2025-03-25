package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite

data class Star(
    val image: String,
    val name: String
)

// 스타 선택 아이템
@Composable
fun StarItem(star: Star) {
    /*
    * 선택된 경우만
    * .border(2.dp, brush = gradientBlue, RoundedCornerShape(18.dp))
    * */

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .background(MainWhite, shape = RoundedCornerShape(18.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 28.dp, end = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(star.image)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Background Image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .background(LightGray)
                    .border(1.dp, color = MainBlack, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(text = star.image, fontSize = 20.sp, color = MainTextBlue, fontWeight = FontWeight.Bold)
            Text(text = star.name, fontSize = 24.sp, color = MainBlack, fontWeight = FontWeight.Normal)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun StarItemPreview() {
    StarItem(
        star = Star("", "이찬원")
    )
}