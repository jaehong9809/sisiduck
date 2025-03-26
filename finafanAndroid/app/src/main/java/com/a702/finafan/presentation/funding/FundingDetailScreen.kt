package com.a702.finafan.presentation.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.component.Card
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CustomGradBottomButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.common.utils.StringUtil
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

    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(MainWhite)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MainWhite)
        ) {
            CommonBackTopBar(modifier = Modifier.background(Color.Transparent), imageOnClick = {}, text = "모금 보기")

            FundingDetailHeader(funding, star, colorSet)

            Column(modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgLightGray)
                .padding(horizontal = 20.dp)) {
                MenuTitle("모금 상세", modifier = Modifier)
                Card("주최자가 입력한 내용", modifier = Modifier.fillMaxWidth())
                Column(modifier = Modifier) {
                    Text("진행자 정보")
                    Row() {
                        Text("동그란 이미지")
                        Column() {
                            Text("진행자 정보")
                            Text("모금 진행 이력")
                        }
                    }
                }
            }
        }
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