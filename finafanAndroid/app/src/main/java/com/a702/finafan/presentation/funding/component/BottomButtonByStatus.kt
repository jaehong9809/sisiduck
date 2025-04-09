package com.a702.finafan.presentation.funding.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.component.GradSelectBottomButton
import com.a702.finafan.domain.funding.model.FundingStatus
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun BottomButtonByStatus(
    status: FundingStatus?,
    isHost: Boolean,
    isParticipant: Boolean,
    colorSet: List<Color>,
    fundingId: Long,
    onNavigate: (String) -> Unit
) {
    when (status) {
        FundingStatus.CANCELED, FundingStatus.FAILED -> {
            // 바텀 버튼 없음
        }
        FundingStatus.INPROGRESS -> {
            when {
                isHost -> GradSelectBottomButton(
                    modifier = Modifier,
                    onLeftClick = { onNavigate(NavRoutes.FundingDeposit.route) },
                    onRightClick = { /* TODO: 해지 로직 */ },
                    left = "입금하기",
                    right = "모금 해지",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
                isParticipant -> GradSelectBottomButton(
                    modifier = Modifier,
                    onLeftClick = { onNavigate(NavRoutes.FundingDeposit.route) },
                    onRightClick = { /* TODO: 참여 취소 로직 */ },
                    left = "입금하기",
                    right = "참여 취소하기",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
                else -> CustomGradBottomButton(
                    onClick = { onNavigate(NavRoutes.FundingJoin.withId(fundingId)) },
                    text = "참가하기",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
            }
        }
        FundingStatus.SUCCESS -> {
            if (isHost) {
                CustomGradBottomButton(
                    onClick = { /* TODO: 증빙 제출 */ },
                    text = "증빙 서류 제출하기",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
            } else if (isParticipant) {
                CustomGradBottomButton(
                    onClick = { /* TODO: 진행 상황 보기 */ },
                    text = "진행 상황 보기",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
            }
        }
        FundingStatus.TERMINATED -> {
            if (isHost || isParticipant) {
                CustomGradBottomButton(
                    onClick = { /* TODO: 모금 결과 보기 */ },
                    text = "모금 결과 보기",
                    gradientColor = listOf(colorSet[0], colorSet[2])
                )
            }
        }
        null -> {}
    }
}
