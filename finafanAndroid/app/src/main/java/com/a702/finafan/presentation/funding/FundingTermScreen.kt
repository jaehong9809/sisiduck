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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.GradSelectBottomButton
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun FundingTermScreen(
    navController: NavController,
    fundingId: Int
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
            onLeftClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("agreement", true)
                joinFunding(1, fundingId) // 모금 참가 API
                navController.popBackStack()
            },
            onRightClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("agreement", false)
                navController.popBackStack()
            },
            left = "동의",
            right = "동의 안함",
        )
    }


}

fun joinFunding(userId: Int, fundingId: Int) : Boolean {
    return true
}

//@Composable
//@Preview
//fun FundingTermScreenPreview() {
//    FundingTermScreen()
//}