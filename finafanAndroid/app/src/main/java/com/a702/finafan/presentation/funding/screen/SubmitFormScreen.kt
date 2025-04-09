package com.a702.finafan.presentation.funding.screen

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.component.ImageField
import com.a702.finafan.common.ui.component.LiveTextArea
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.starGradSkyBlue
import com.a702.finafan.common.ui.theme.starGradTurquoise
import com.a702.finafan.presentation.funding.component.FundingInfoHeader
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.component.SpendingListSection
import com.a702.finafan.presentation.funding.component.SuccessBadge
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel

@Composable
fun SubmitFormScreen(
    navController: NavController,
    fundingDetailViewModel: FundingDetailViewModel
) {
    val fundingState by fundingDetailViewModel.uiState.collectAsState()

    val image = remember { mutableStateOf(Uri.EMPTY) }
    val description = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = "성공 모금 종료하기"
            )
        },
        bottomBar = {
            CustomGradBottomButton(
                onClick = {
                    // TODO: 버튼 클릭 시 처리
                },
                text = "완료",
                isEnabled = true,
                gradientColor = listOf(starGradSkyBlue, starGradTurquoise)
            )
        },
        containerColor = MainWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            fundingState.funding?.let { FundingInfoHeader(funding = it, showRemainingAmount = false, showDetailButton = false) }

            SuccessBadge(modifier = Modifier.align(Alignment.CenterHorizontally))

            MenuTitle(content = "증빙 서류 첨부")
            MenuDescription(content = "선지출한 금액이 있다면 증명 자료를 제출해주세요.")
            ImageField(
                modifier = Modifier.padding(top = 34.dp),
                label = "",
                selectImage = image
            )

            MenuTitle(content = "지출 내역")
            MenuDescription(content = "증빙한 금액과 추후에 지출할 내역을 자세하게 적어주세요.")
            SpendingListSection()

            MenuTitle(content = "안내 사항")
            MenuDescription(content = "참가자들에게 전달할 사항이 있다면 상세하게 적어 주세요.")
            LiveTextArea(
                placeholder = "내용",
                description = description,
                onValueChange = {}
            )
        }
    }
}
