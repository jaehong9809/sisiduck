package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.funding.model.Star
import com.a702.finafan.presentation.funding.component.FundingCreateButton
import com.a702.finafan.presentation.funding.component.FundingList
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.component.ScreenTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun FundingScreen(
    navController: NavHostController,
    fundingViewModel: FundingViewModel
) {
    val uiState by fundingViewModel.uiState.collectAsState()
    val myStars: List<Star> = getMyStars()

    LaunchedEffect(Unit) {
        fundingViewModel.fetchAllFundings()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MainBgLightGray)
            .padding(14.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        ScreenTitle("모금", modifier = Modifier.padding(start = 6.dp, top = 20.dp, bottom = 10.dp))
        FundingCreateButton(
            onClick = {
                navController.navigate(NavRoutes.FundingCreate.route)
            },
            modifier = Modifier.padding(bottom = 10.dp)
        )
        MenuTitle("모금 보기", modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp))
        ThreeTabRow(
            labels = listOf("전체", "참여", "내 모금"),
            containerColor = MainWhite,
            selectedTabColor = MainBlack,
            onTabSelected = listOf(
                { fundingViewModel.fetchAllFundings() },
                { fundingViewModel.fetchParticipatingFundings() },
                { fundingViewModel.fetchMyFundings() }
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        } else {
            FundingList(uiState.fundings, navController)
        }
    }
}

fun getMyStars(): List<Star> {
    return listOf(
        Star(id = 101,
            name = "임영웅",
            index = 0,
            thumbnail = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/limyeongwoong.png"),
        Star(id = 102,
            name = "이찬원",
            index = 1,
            thumbnail = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/leechanwon.png",
            image = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/image12.png"),
    )
}