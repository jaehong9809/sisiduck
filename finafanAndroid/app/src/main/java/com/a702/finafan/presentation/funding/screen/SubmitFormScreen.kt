package com.a702.finafan.presentation.funding.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.component.DialogLayout
import com.a702.finafan.common.ui.component.LiveTextArea
import com.a702.finafan.common.ui.component.MultiImageField
import com.a702.finafan.common.ui.theme.CustomTypography.bodySmall
import com.a702.finafan.common.ui.theme.CustomTypography.displaySmall
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.TermBoxGray
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.common.utils.StringUtil
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

    val uploadedImages = remember { mutableStateListOf<Uri>() }
    val spendingItems = remember { mutableStateListOf<Pair<String, String>>() }
    val description = remember { mutableStateOf("") }

    val showDialog = remember { mutableStateOf(false) }

    val spendingTotal = spendingItems.sumOf { it.second.replace(",", "").replace("원", "").toIntOrNull() ?: 0 }
    val totalAmount = fundingState.funding?.currentAmount ?: 0L

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = "성공 모금 종료하기"
            )
        },
        bottomBar = {
            CustomGradBottomButton(
                onClick = {
                    // TODO: API 호출, dialog 보여주고 모금 상세 화면으로 돌아가기
                    showDialog.value = true
                },
                text = "종료",
                isEnabled = spendingTotal >= totalAmount,
                gradientColor = fundingState.colorSet
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

            SuccessBadge(size = 120.dp, modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(20.dp))

            MenuTitle(content = "증빙 서류 첨부", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "지출한 금액에 대해 사진 또는 스크린샷으로 증빙해 주세요. " +
                    "예) 영수증, 현장 사진")

            MultiImageField(
                label = "",
                selectImages = uploadedImages
            )
            Spacer(Modifier.height(30.dp))

            MenuTitle(content = "지출 내역", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "증빙한 금액과 추후에 지출할 내역을 자세하게 적어주세요.")
            SpendingListSection(
                items = spendingItems,
                onItemsChanged = {
                    spendingItems.clear()
                    spendingItems.addAll(it)
                },
                modifier = Modifier.padding(top = 20.dp)
            )

            if (spendingTotal < totalAmount) {
                val diff = totalAmount - spendingTotal
                Text(
                    text = StringUtil.formatCurrency(diff) + "만큼 더 정산해 주세요!",
                    color = fundingState.colorSet[1],
                    style = Typography.displayLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 20.dp)
                )
            }


            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = TermBoxGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text("💁 유의사항", style = displaySmall)
                    Text("• 전체 모금액 이상 기재해야 종료 버튼이 활성화되어 정상 종료가 가능합니다.\n" +
                            "• 작성한 내용은 자동 저장되므로, 종료 전까지 내용을 자유롭게 추가 및 삭제 해주세요.",
                        style = bodySmall)
                }
            }

            Spacer(Modifier.height(30.dp))

            MenuTitle(content = "안내 사항", modifier = Modifier.padding(vertical = 10.dp))
            MenuDescription(content = "참가자들에게 전달할 사항이 있다면 상세하게 적어 주세요.",)
            LiveTextArea(
                placeholder = "내용",
                description = description,
                onValueChange = {},
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        DialogLayout(
            showDialog = showDialog,
            confirmBtnText = "확인",
            onClickConfirm = {
                showDialog.value = false
                navController.popBackStack()
            }
        ) {
            Spacer(Modifier.height(20.dp))
            Text("모금이 성공적으로 종료되었습니다!", style = Typography.titleLarge)
        }
    }
}
