package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.component.CircleBorderImage
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.gradientBlue
import com.a702.finafan.domain.savings.model.Star

// 스타 선택 아이템
@Composable
fun StarItem(star: Star, isSelected: Boolean, onSelect: (Star) -> Unit) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .background(MainWhite, shape = RoundedCornerShape(18.dp))
        .then(
            if (isSelected) {
                Modifier
                    .border(2.dp, brush = gradientBlue, shape = RoundedCornerShape(18.dp))
            } else {
                Modifier
            }
        )
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onSelect(star) }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 28.dp, end = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            CircleBorderImage(
                modifier = Modifier.size(50.dp),
                imageUrl = star.entertainerProfileUrl
            )

            Text(
                text = star.entertainerName,
                fontSize = 24.sp,
                color = MainBlack,
                fontWeight = FontWeight.Normal
            )
        }
    }

}

@Preview()
@Composable
fun StarItemPreview() {
    StarItem(
        star = Star(),
        isSelected = true,
        onSelect = {}
    )
}