package com.a702.finafan.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.a702.finafan.presentation.funding.screen.FundingCreateScreen
import com.a702.finafan.presentation.funding.screen.FundingDetailScreen
import com.a702.finafan.presentation.funding.screen.FundingScreen
import com.a702.finafan.presentation.funding.screen.FundingTermScreen
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel

fun NavGraphBuilder.fundingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Funding.route
    ) {
        composable(NavRoutes.FundingMain.route) {
            val fundingViewModel: FundingViewModel = hiltViewModel()
            FundingScreen(navController, fundingViewModel)
        }

        composable(NavRoutes.FundingDetail.route + "/{fundingId}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getString("fundingId")?.toLongOrNull() ?: 0L
            FundingDetailScreen(fundingId)
        }

        composable(NavRoutes.FundingJoin.route + "/{fundingId}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getString("fundingId")?.toLongOrNull() ?: 0L
            FundingTermScreen(navController, fundingId)
        }

        composable(NavRoutes.FundingCreate.route) { backStackEntry ->
            val fundingCreateViewModel: FundingCreateViewModel = hiltViewModel()
            FundingCreateScreen(navController, fundingCreateViewModel)
        }
    }
}