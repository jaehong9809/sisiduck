package com.a702.finafan.presentation.funding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.GradSelectBottomButton
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel

@Composable
fun FundingTermScreen(
    navController: NavController,
    fundingId: Long
) {
    val viewModel: FundingDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

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
                viewModel.joinFunding(fundingId) // 모금 참가 API
//                viewModel.fetchFundingDetail(fundingId)
//                Log.d("joinFunding: (TermScreen) ", "${viewModel.uiState.value.isParticipant}")
//                navController.popBackStack()
            },
            onRightClick = {
                navController.popBackStack()
            },
            left = "동의",
            right = "동의 안함",
        )
    }
    LaunchedEffect(uiState.isParticipant) {
        if (uiState.isParticipant) {
            Log.d("joinFunding: (TermScreen)", "isParticipant 변경 감지됨, 화면 닫기")
            navController.popBackStack()
        }
    }
}

//@Composable
//@Preview
//fun FundingTermScreenPreview() {
//    FundingTermScreen()
//}