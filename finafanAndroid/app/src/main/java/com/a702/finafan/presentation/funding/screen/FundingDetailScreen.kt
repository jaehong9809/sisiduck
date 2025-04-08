package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.BottomSheetLayout
import com.a702.finafan.common.ui.component.Card
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.component.CustomGradButton
import com.a702.finafan.common.ui.component.GradSelectBottomButton
import com.a702.finafan.common.ui.component.ImageItem
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.FundingStatus
import com.a702.finafan.presentation.funding.component.DepositHistoryList
import com.a702.finafan.presentation.funding.component.FundingDetailHeader
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun FundingDetailScreen(
    fundingId: Long,
    viewModel: FundingDetailViewModel
) {
    val navController = LocalNavController.current

    val uiState by viewModel.uiState.collectAsState()

    val showBottomSheet = remember { mutableStateOf(false) }

    val bottomSheetMessage = when (uiState.funding?.let { FundingStatus.valueOf(it.status) }) {
        FundingStatus.CANCELED -> "주최자에 의해 중단된 펀딩입니다."
        FundingStatus.FAILED -> "목표 금액을 달성하지 못해 종료된 펀딩입니다."
        else -> ""
    }

    LaunchedEffect(Unit) {
        viewModel.fetchFundingDetail(fundingId)
        viewModel.fetchDepositHistory(fundingId, DepositFilter.ALL)
        val status = uiState.funding?.let { FundingStatus.valueOf(it.status) }
        if (status == FundingStatus.CANCELED || status == FundingStatus.FAILED) {
            showBottomSheet.value = true
        }
    }

    val colorSet: List<Color> = listOf(
        starThemes[uiState.funding?.star?.index?:0].start,
        starThemes[uiState.funding?.star?.index?:0].mid,
        starThemes[uiState.funding?.star?.index?:0].end
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainWhite)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 60.dp)
        ) {
            Box {
                uiState.funding?.let {
                    FundingDetailHeader(it, it.star, colorSet)
                }

                CommonBackTopBar(
                    text = "모금 보기",
                    backgroundColor = Color.Transparent,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }

            if(uiState.isParticipant) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MainWhite)
                        .padding(horizontal = 14.dp)
                ) {
                    CustomGradButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 26.dp, horizontal = 6.dp),
                        onClick = {},
                        text = "모금 상세",
                        gradientColor = listOf(colorSet[0], colorSet[2])
                    )
                    MenuTitle(
                        content = "모금 내역", modifier = Modifier.padding(
                            horizontal = 6.dp
                        )
                    )
                    ThreeTabRow(
                        labels = listOf("전체", "나의 내역"),
                        containerColor = MainWhite,
                        selectedTabColor = MainBlack,
                        onTabSelected = listOf(
                        { viewModel.fetchDepositHistory(fundingId, DepositFilter.ALL) },
                        { viewModel.fetchDepositHistory(fundingId, DepositFilter.MY) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 22.dp)
                    )
                    DepositHistoryList(uiState.deposits)

                }
            } else {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MainBgLightGray)
                    .padding(horizontal = 20.dp)) {
                    MenuTitle("모금 상세", modifier = Modifier.padding(top = 26.dp, bottom = 18.dp))
                    Card("주최자가 입력한 내용", modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp))
                    Column(modifier = Modifier) {
                        Text(text = "진행자 정보", style = Typography.displaySmall,
                            modifier = Modifier.padding(bottom = 5.dp))
                        Row() {
                            ImageItem(Modifier.padding(5.dp), { }, R.drawable.user_circle)
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(text = "진행자 정보",
                                    fontSize = 14.sp,
                                    fontFamily = Pretendard,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(vertical = 5.dp))
                                Text("✅ 모금 진행 이력", style = Typography.labelLarge)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
        if(uiState.isParticipant) {
            GradSelectBottomButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onLeftClick = {
                    navController.navigate(NavRoutes.FundingDeposit.route)
                },
                onRightClick = {},
                left = "입금하기",
                right = "참여 취소하기",
                gradientColor = listOf(
                    colorSet[0], colorSet[2]
                )
            )
        } else {
            CustomGradBottomButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onClick = {
                    navController.navigate(NavRoutes.FundingJoin.route + "/${fundingId}")
                },
                text = "참가하기",
                gradientColor = listOf(
                    colorSet[0], colorSet[2]
                )
            )
        }

        BottomSheetLayout(
            showBottomSheet = showBottomSheet,
            confirmBtnText = "확인",
            onClickConfirm = { showBottomSheet.value = false }
        ) {
            Text(text = bottomSheetMessage)
        }
    }
}