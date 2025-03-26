package com.a702.finafan.presentation.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.GradSelectBottomButton
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun FundingTermScreen(
//    funding: Funding
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainWhite)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MainWhite)
        ) {
            CommonBackTopBar(
                modifier = Modifier,
                imageOnClick = {},
                text = "상품 이용 약관",
                isTextCentered = true
            )
            Box(
                modifier = Modifier.padding(25.dp)
            ) {
                Text("이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관" +
                        " 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관" +
                        " 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관 이용 약관")
            }
        }
        GradSelectBottomButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = {},
            left = "동의",
            right = "동의 안함",
        )
    }


}

@Composable
@Preview
fun FundingTermScreenPreview() {
    FundingTermScreen()
}