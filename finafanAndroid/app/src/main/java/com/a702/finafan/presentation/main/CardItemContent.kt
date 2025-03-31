package com.a702.finafan.presentation.main

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBgGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.starThemes

@Composable
fun LoginContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Icon Description",
            tint = Color.Unspecified,
            modifier = Modifier.size(120.dp)
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp)
                .background(MainBgLightGray, shape = RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ){
            Text(text = stringResource(R.string.card_login_button),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                fontFamily = Pretendard,
                color = MainBlack
            )
        }
    }
}

@Composable
fun CreateSavingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Icon(
                painter = painterResource(id = R.drawable.opened_box),
                contentDescription = "Icon Description",
                tint = Color.Unspecified,
                modifier = Modifier.width(120.dp)
                    .padding(top = 25.dp)
            )
        Text(text = stringResource(R.string.card_create_saving_info),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MainTextGray,
            fontFamily = Pretendard,
            modifier = Modifier.padding(top = 20.dp, bottom = 13.dp))
        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(MainGradBlue, MainGradViolet)))
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.card_create_saving_button),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainWhite)
        }
    }
}

@Composable
fun SavingContent(savingName: String) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row() {
            Column() {
                Text("이찬원 스타적금")
                Text("123-455-124535")
                Text("154,000원")
            }
            Icon(
                painter = painterResource(id = R.drawable.opened_box),
                contentDescription = "Icon Description",
                tint = Color.Unspecified,
                modifier = Modifier.width(120.dp)
                    .padding(top = 25.dp)
            )
        }
    }
    Box(
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(MainGradBlue, MainGradViolet)))
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.card_create_saving_button),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainWhite)
    }
}
