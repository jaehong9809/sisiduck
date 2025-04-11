package com.a702.finafan.presentation.funding.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.presentation.funding.component.FundingCreateButton
import com.a702.finafan.presentation.funding.component.FundingList
import com.a702.finafan.presentation.funding.component.MenuTitle
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun FundingScreen(
    navController: NavHostController,
    fundingViewModel: FundingViewModel
) {
    val fundingState by fundingViewModel.fundingState.collectAsState()
    val selectedFilter by fundingViewModel.selectedFilter.collectAsState()

    LaunchedEffect(Unit) {
        fundingViewModel.fetchFundings(FundingFilter.ALL)
        fundingState.fundings
    }

    LaunchedEffect(selectedFilter) {
        fundingViewModel.fetchFundings(selectedFilter)
    }

    val tabIndex = when (selectedFilter) {
        FundingFilter.ALL -> 0
        FundingFilter.PARTICIPATED -> 1
        FundingFilter.MY -> 2
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = stringResource(R.string.funding),
                backgroundColor = Color.Transparent
            )
        },
        containerColor = MainBgLightGray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(14.dp)
        ) {
            FundingCreateButton(
                onClick = {
                    navController.navigate(NavRoutes.FundingCreate.route)
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )

            MenuTitle(stringResource(R.string.funding_page), modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp))

            ThreeTabRow(
                labels = listOf(stringResource(R.string.funding_list_tap_all),
                    stringResource(R.string.funding_list_tap_participating),
                    stringResource(R.string.funding_list_tap_my)),
                containerColor = MainWhite,
                selectedTabColor = MainBlack,
                onTabSelected = listOf(
                    { fundingViewModel.fetchFundings(FundingFilter.ALL) },
                    { fundingViewModel.fetchFundings(FundingFilter.PARTICIPATED) },
                    { fundingViewModel.fetchFundings(FundingFilter.MY) }
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )

            if (fundingState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                FundingList(fundingState.fundings, navController)
            }
        }
    }
}
