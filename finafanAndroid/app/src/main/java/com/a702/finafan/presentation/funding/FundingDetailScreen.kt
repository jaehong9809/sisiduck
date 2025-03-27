package com.a702.finafan.presentation.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
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
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.presentation.funding.Deposit
import java.time.LocalDate

@Composable
fun FundingDetailScreen(
    star: Star,
    fundingId: Int
) {
    val funding: Funding = getFundingDetail(fundingId)
    val progress: Int = StringUtil.formatPercentage(
        funding.fundingCurrentAmount, funding.fundingGoalAmount
    )
    val colorSet: List<Color> = listOf(
        starThemes[funding.starIndex].start,
        starThemes[funding.starIndex].mid,
        starThemes[funding.starIndex].end
    )

    val isParticipant: Boolean = false
    var deposits by remember { mutableStateOf<List<Deposit>>(getAllDeposits()) }

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
            CommonBackTopBar(modifier = Modifier.background(Color.Transparent), imageOnClick = {}, text = "모금 보기")

            FundingDetailHeader(funding, star, colorSet)

            if(isParticipant) {
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
                    MenuTitle(content = "모금 내역", modifier = Modifier.padding(
                        horizontal = 6.dp
                    ))
                    ThreeTabRow(
                        labels = listOf("전체", "나의 내역"),
                        containerColor = MainWhite,
                        selectedTabColor = MainBlack,
                        onTabSelected = listOf(
                        { deposits = getAllDeposits() },
                        { deposits = getMyDeposits() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 22.dp)
                    )
                    DepositHistoryList(deposits)

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
        if(isParticipant) {
            GradSelectBottomButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                onLeftClick = {},
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
                onClick = {},
                text = "참가하기",
                gradientColor = listOf(
                    colorSet[0], colorSet[2]
                )
            )
        }
    }

}

fun getFundingDetail(id: Int): Funding {
    val fundings: List<Funding> = listOf(
        Funding(
            1, 101, 0, "임영웅",
            "데뷔 5주년 기념 서포트",
            LocalDate.of(2025, 4, 8), 2_000_000, 1_500_000
        ),
        Funding(
            2, 102, 1, "이찬원",
            "뮤비 촬영장 커피차 서포트",
            LocalDate.of(2025, 5, 10), 1_500_000, 1_300_000
        )
    )
    return fundings[id]
}

fun getAllDeposits(): List<Deposit> {
    val deposits = listOf(
        Deposit("2025", "3월 17일", "입금자명", "09:48", 44444),
        Deposit("2025", "3월 16일", "입금자명", "15:34", 44444),
        Deposit("2025", "3월 16일", "입금자명", "15:28", 44444)
    )
    return deposits
}

fun getMyDeposits(): List<Deposit> {
    val deposits = listOf(
        Deposit("2025", "3월 17일", "입금자명", "09:48", 44444)
    )
    return deposits
}

@Preview
@Composable
fun FundingDetailScreenPreview() {
    FundingDetailScreen(
        Star(id = 102,
        name = "이찬원",
        index = 1,
        thumbnail = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/leechanwon.png",
        image = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/image12.png"), 1)
}