package com.a702.finafan.common.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


// 가운데 텍스트, 오른쪽 닫기
@Composable
fun CommonCloseTopBar(
    modifier: Modifier = Modifier,
    imageOnClick: () -> Unit,
    text: String? = null
) {
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
            ImageItem(modifier, imageOnClick, R.drawable.icon_close)
        }
    }
}

// (왼쪽 뒤로가기), (왼쪽 뒤로가기, 오른쪽 텍스트), (왼쪽 뒤로가기, 가운데 텍스트)
@Composable
fun CommonBackTopBar(
    modifier: Modifier = Modifier,
    imageOnClick: () -> Unit,
    textOnClick: () -> Unit,
    text: String? = null,
    isTextCentered: Boolean = false
) {
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
            ImageItem(modifier, imageOnClick, R.drawable.arrow_left)
        }

        TextItem(modifier = if (isTextCentered) Modifier.align(Alignment.Center) else Modifier.align(Alignment.CenterEnd), textOnClick, text)
    }
}

@Composable
fun ImageItem(modifier: Modifier, onClick: () -> Unit, @DrawableRes imageId: Int) {
    Image(
        modifier = modifier.clickable { onClick() },
        painter = painterResource(id = imageId),
        contentDescription = "back",
    )
}

@Composable
fun TextItem(modifier: Modifier, onClick: () -> Unit?, text: String?) {
    text?.let {
        Text(
            modifier = modifier.clickable { onClick() },
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
            imageOnClick = {},
            textOnClick = {},
            text = "텍스트")

        CommonBackTopBar(
            imageOnClick = {},
            textOnClick = {},
            text = "텍스트",
            isTextCentered = true)

        CommonBackTopBar(
            imageOnClick = {},
            textOnClick = {},)

        CommonCloseTopBar(
            imageOnClick = {},
            text = "텍스트")
    }

}