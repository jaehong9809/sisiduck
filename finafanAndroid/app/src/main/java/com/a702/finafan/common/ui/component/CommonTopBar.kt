package com.a702.finafan.common.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.navigation.LocalNavController

// 가운데 텍스트, 오른쪽 닫기
@Composable
fun CommonCloseTopBar(
    modifier: Modifier = Modifier,
    text: String? = null
) {
    val navController = LocalNavController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MainWhite)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        TextItem(modifier = Modifier.align(Alignment.Center), onClick = {}, text)

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageItem(
                modifier,
                onClick = { navController.popBackStack() },
                R.drawable.icon_close
            )
        }
    }
}

// (왼쪽 뒤로가기), (왼쪽 뒤로가기, 오른쪽 텍스트), (왼쪽 뒤로가기, 가운데 텍스트)
@Composable
fun CommonBackTopBar(
    modifier: Modifier = Modifier,
    textOnClick: (() -> Unit)? = null,
    text: String? = null,
    isTextCentered: Boolean = false
) {
    val navController = LocalNavController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MainWhite)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageItem(
                modifier,
                onClick = { navController.popBackStack() },
                R.drawable.arrow_left
            )
        }

        TextItem(modifier = if (isTextCentered) Modifier.align(Alignment.Center) else Modifier.align(Alignment.CenterEnd), textOnClick, text)
    }
}

@Composable
fun ImageItem(modifier: Modifier, onClick: () -> Unit, @DrawableRes imageId: Int) {
    Image(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        painter = painterResource(id = imageId),
        contentDescription = "back",
    )
}

@Composable
fun TextItem(modifier: Modifier, onClick: (() -> Unit)? = null, text: String?) {
    text?.let {
        Text(
            modifier = if (onClick != null) {
                modifier.clickable { onClick() }
            } else {
                modifier
            },
            text = it,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MainBlack,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EndTextPreview() {
    Column {
        CommonBackTopBar(
            textOnClick = {},
            text = "텍스트")

        CommonBackTopBar(
            textOnClick = {},
            text = "텍스트",
            isTextCentered = true)

        CommonBackTopBar(
            textOnClick = {},)

        CommonCloseTopBar(
            text = "텍스트")
    }

}