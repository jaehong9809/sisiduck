package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.SavingDescBg
import com.a702.finafan.common.ui.theme.SavingDescGray
import com.a702.finafan.common.ui.theme.gradientBlue

// 적금 상품 설명 화면
@Composable
fun SavingDescScreen() {

    Column(
        modifier = Modifier.fillMaxSize().background(MainWhite)
    ) {
        CommonBackTopBar(imageOnClick = {}, text = stringResource(R.string.saving_item_title), isTextCentered = true)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = CenterHorizontally,
        ) {
            FirstDesc()
            SecondDesc()
            ThirdDesc()

            // TODO: 연결된 입출금 계좌가 있을 경우 가입 페이지로 이동, 아닐 경우 1원 송금 페이지로 이동
            PrimaryGradButton(
                onClick = {},
                text = stringResource(R.string.btn_join),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 32.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
        }

    }
}

// 첫 번째 설명
@Composable
fun FirstDesc() {
    Column(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp, top = 28.dp, bottom = 50.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = "사랑하는만큼 모으는",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = MainBlack,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.saving_item_title),
            style = TextStyle(
                brush = gradientBlue,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Pretendard
            )
        )

        Text(
            text = "스타와의 추억을\n순간마다 남기는 저축 기록",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp,
            color = MainTextGray,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

// 두 번째 설명
@Composable
fun SecondDesc() {
    Column(
        modifier = Modifier
            .background(SavingDescBg)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp, top = 28.dp, bottom = 50.dp),
            horizontalAlignment = CenterHorizontally,
        ) {
            Text(
                text = "사랑을\n모았던 순간의",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 44.sp,
                color = SavingDescGray,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "기록은 영원히",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MainBlack,
            )

            Text(
                text = "응원글을 남겨보세요.\n사랑을 모으는 순간에도, \n사랑을 돌려받는 순간에도",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                color = MainTextGray,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Column(
                modifier = Modifier.padding(top = 32.dp)
            ) {
                DescItem("오늘도 고마워", "5,000원")
                DescItem("이렇게 귀여울수가", "10,000원")
            }
        }
    }
}

// 세 번째 설명
@Composable
fun ThirdDesc() {
    Column(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp, top = 38.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(38.dp)
    ) {
        Text(
            text = "이제 기록을 시작해 볼까요?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = MainBlack,
        )

        TermsButtonList()
    }
}

@Composable
fun DescItem(title: String, money: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(25.dp))
            .height(74.dp)
            .background(MainWhite),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = title, fontSize = 18.sp, color = MainBlack, fontWeight = FontWeight.Medium)
            Text(text = money, fontSize = 18.sp, color = MainTextBlue, fontWeight = FontWeight.Bold)
        }
    }

}

// 약관 리스트
@Composable
fun TermsButtonList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TermGuideItem("상품 안내", onClick = {})
        TermGuideItem("금리 안내", onClick = {})
        TermGuideItem("이용 약관", onClick = {})
        TermGuideItem("상품 설명서", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SavingDescPreview() {
    SavingDescScreen()
}

