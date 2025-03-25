package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.BtnBgGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainWhite

/* 메인 메뉴용 IconButton */
@Composable
fun MainSquareIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    text: String? = null
) {
    Box(
        modifier = modifier
            .size(156.dp)
            .background(MainWhite, shape = RoundedCornerShape(24.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            icon?.invoke()
            text?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainBlack,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

/* 덕순이 IconButton */
@Composable
fun MainWideIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    text: String
) {
    Box(
        modifier = modifier
            .width(312.dp)
            .height(156.dp)
            .background(MainWhite, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            icon?.invoke()
            text.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainBlack,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

/* Radius, drop-shadow 있음 */
@Composable
fun PrimaryGradButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String? = null
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(MainGradBlue, MainGradViolet)
    )

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 320.dp, minHeight = 60.dp)
            .clickable { onClick() }
            .background(gradient, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        text?.let {
            Text(
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainWhite
            )
        }
    }
}

@Composable
fun PrimaryGradBottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String? = null,
    isEnabled: Boolean = true
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(MainGradBlue, MainGradViolet)
    )

    val gray = Brush.horizontalGradient(
        colors = listOf(BtnBgGray, BtnBgGray)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(enabled = isEnabled) { onClick() }
            .background(if (isEnabled) gradient else gray),
        contentAlignment = Alignment.Center
    ) {
        text?.let {
            Text(
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainWhite
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SquareButtonPreview() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MainSquareIconButton(
            onClick = { /* 클릭 액션 */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "Home"
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MainSquareIconButton(
            onClick = { /* onClick Action */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "주변 팬 찾기"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonsPreview() {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryGradButton(
                onClick = {},
                text = "Primary Button"
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            PrimaryGradBottomButton(
                onClick = {},
                text = "Bottom Button"
            )
        }
    }
}