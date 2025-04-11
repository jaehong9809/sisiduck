package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.component.CircleBorderImage
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainBlackTransparency
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.gradientBlue
import com.a702.finafan.domain.savings.model.Star

// 스타 선택 아이템
@Composable
fun StarItem(star: Star, isSelected: Boolean, onSelect: (Star) -> Unit) {
    val backgroundModifier = if (isSelected) {
        Modifier
            .border(
                width = 2.dp,
                brush = gradientBlue,
                shape = RoundedCornerShape(18.dp)
            )
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Color(0x335E9BFF),
                spotColor = Color(0x335E9BFF)
            )
    } else {
        Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = MainBlackTransparency,
                spotColor = MainBlackTransparency
            )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
            .then(backgroundModifier)
            .background(MainWhite, shape = RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onSelect(star) }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleBorderImage(
                modifier = Modifier
                    .size(60.dp),
                imageUrl = star.starImageUrl
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = star.starName,
                fontSize = 24.sp,
                color = MainBlack,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
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