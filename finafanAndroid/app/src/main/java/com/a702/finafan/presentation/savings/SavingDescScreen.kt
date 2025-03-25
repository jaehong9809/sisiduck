package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.TermBoxGray
import com.a702.finafan.common.ui.theme.TermTextGray

@Composable
fun SavingDescScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(MainWhite)
    ) {
        CommonBackTopBar(imageOnClick = {}, textOnClick = {}, text = "스타 적금", isTextCentered = true)

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // 첫 번째 설명
            Column {

            }

            // 두 번째 설명

            // 세 번째 설명
            Column(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, top = 38.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(58.dp)
            ) {
                Text(
                    text = "이제 기록을 시작해 볼까요?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainBlack,
                )

                TermsButtonList()
            }

            PrimaryGradButton(
                onClick = {},
                text = "가입하기",
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 하단 중앙에 고정
                    .padding(bottom = 16.dp)
            )
        }


    }
}

@Composable
fun TermsButtonList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TermsButton("상품 안내", onClick = {})
        TermsButton("금리 안내", onClick = {})
        TermsButton("이용 약관", onClick = {})
        TermsButton("상품 설명서", onClick = {})
    }
}

@Composable
fun TermsButton(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(TermBoxGray)
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = TermTextGray,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.clickable { onClick() },
                painter = painterResource(id = R.drawable.arrow_right_gray),
                contentDescription = "back",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavingDescPreview() {
    SavingDescScreen()
}

