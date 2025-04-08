package com.a702.finafan.presentation.funding.screen

import android.util.Log
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
import com.a702.finafan.domain.funding.model.FundingFilter
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

    LaunchedEffect(Unit) {
        fundingViewModel.fetchFundings(FundingFilter.ALL)
        uiState.fundings
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MainBgLightGray)
            .padding(14.dp)
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
                { fundingViewModel.fetchFundings(FundingFilter.ALL) },
                { fundingViewModel.fetchFundings(FundingFilter.PARTICIPATED) },
                { fundingViewModel.fetchFundings(FundingFilter.MY) }
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
            Log.d("uiState.fundings: ", uiState.fundings.toString())
            FundingList(uiState.fundings, navController)
        }
    }
}