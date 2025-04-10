package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.starGradTurquoise

@Composable
fun SuccessBadge(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    color: Color? = starGradTurquoise
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = MainWhite,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 2.dp,
                color = color?: starGradTurquoise,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = "check",
                modifier = Modifier
                    .size(size * 0.7f)
                    .padding(bottom = 5.dp)
            )
            Text(
                text = stringResource(R.string.success_badge_text),
                color = color?: starGradTurquoise,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}
