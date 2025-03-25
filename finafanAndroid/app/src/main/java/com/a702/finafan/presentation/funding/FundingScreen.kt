package com.a702.finafan.presentation.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBgBlueGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

//* 나중에 지울 임시 DTO *//
data class Star(
    val id: Int,       // 스타 ID
    val name: String,  // 스타 이름
    val index: Int,     // 내가 담은 스타 중 몇 번째인지 (0~2)
    val image: String
)

data class Funding(
    val id: Int,
    val starId: Int,
    val starIndex: Int,
    val starName: String,
    val fundingTitle: String,
    val fundingEndDate: LocalDate,
    val fundingGoalAmount: Int,
    val fundingCurrentAmount: Int,
)

@Composable
fun FundingScreen() {

    val myStars: List<Star> = getMyStars()
    var fundings by remember { mutableStateOf<List<Funding>>(getAllFundings()) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MainBgBlueGray)
    ) {
        ScreenTitle("모금")
        FundingAddButton(
            onClick = {}
        )
        MenuTitle("모금 보기")
        ThreeTabRow(
            labels = listOf("전체", "참여", "내 모금"),
            containerColor = MainWhite,
            selectedTabColor = MainBlack,
            onTabSelected = listOf(
                { fundings = getAllFundings() },
                { fundings = getParticipatingFundings() },
                { fundings = getMyFundings() }
            ),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp)
        )
        FundingList(fundings, myStars)
    }
}

@Preview(showBackground = true)
@Composable
fun FundingScreenPreview() {
    FundingScreen()
}

fun getAllFundings(): List<Funding> {
    println("모든 모금 조회 API 호출")
    return listOf(
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
}

fun getParticipatingFundings(): List<Funding> {
    println("참여 중인 모금 조회 API 호출")
    return listOf(
        Funding(
        2, 102, 1, "이찬원",
        "뮤비 촬영장 커피차 서포트",
            LocalDate.of(2025, 5, 10), 1_500_000, 1_300_000
        )
    )
}

fun getMyFundings(): List<Funding> {
    println("내가 생성한 모금 조회 API 호출")
    return listOf(Funding(
        1, 101, 0, "임영웅",
        "데뷔 5주년 기념 서포트",
        LocalDate.of(2025, 4, 8), 2_000_000, 1_500_000)
    )
}

fun getMyStars(): List<Star> {
    return listOf(
        Star(101, "임영웅", 0, "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/limyeongwoong.png"),
        Star(102, "이찬원", 1, "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/leechanwon.png"),
    );
}