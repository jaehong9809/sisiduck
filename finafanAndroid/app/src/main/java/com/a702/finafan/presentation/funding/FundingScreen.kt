package com.a702.finafan.presentation.funding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun FundingScreen() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        ScreenTitle("모금")
        FundingAddButton(
            onClick = {}
        )
        MenuTitle("모금 보기")
        ThreeTabRow(
            labels = listOf("전체", "참여", "내 모금"),
            containerColor = MainWhite,
            selectedTabColor = Color.Blue,
            onTabSelected = listOf(
                { getAllFundings() },  // 첫 번째 탭: 모든 모금 조회
                { getParticipatingFundings() },  // 두 번째 탭: 참여 중인 모금 조회
                { getMyFundings() }  // 세 번째 탭: 내가 만든 모금 조회
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FundingScreenPreview() {
    FundingScreen()
}

fun getAllFundings() {
    println("모든 모금 조회 API 호출")
}

fun getParticipatingFundings() {
    println("참여 중인 모금 조회 API 호출")
}

fun getMyFundings() {
    println("내가 생성한 모금 조회 API 호출")
}