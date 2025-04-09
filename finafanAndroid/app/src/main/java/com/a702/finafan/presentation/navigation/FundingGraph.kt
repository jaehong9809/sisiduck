package com.a702.finafan.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.funding.screen.FundingCreateScreen
import com.a702.finafan.presentation.funding.screen.FundingDepositScreen
import com.a702.finafan.presentation.funding.screen.FundingDetailScreen
import com.a702.finafan.presentation.funding.screen.FundingScreen
import com.a702.finafan.presentation.funding.screen.FundingTermScreen
import com.a702.finafan.presentation.funding.screen.SubmitFormScreen
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel

fun NavGraphBuilder.fundingGraph(
    navController: NavHostController,
    fundingViewModel: FundingViewModel,
    fundingCreateViewModel: FundingCreateViewModel,
    fundingDetailViewModel: FundingDetailViewModel,
    accountViewModel: AccountViewModel
) {
    navigation(
        startDestination = NavRoutes.FundingMain.route,
        route = NavRoutes.Funding.route
    ) {
        // 펀딩 메인
        composable(NavRoutes.FundingMain.route) {
            FundingScreen(navController, fundingViewModel)
        }

        // 펀딩 상세
        composable(
            route = NavRoutes.FundingDetail.route,
            arguments = listOf(navArgument("fundingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getLong("fundingId") ?: 0L
            FundingDetailScreen(fundingId, fundingDetailViewModel)
        }

        // 펀딩 참여
        composable(
            route = NavRoutes.FundingJoin.route,
            arguments = listOf(navArgument("fundingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getLong("fundingId") ?: 0L
            FundingTermScreen(navController, fundingId)
        }

        // 펀딩 생성
        composable(NavRoutes.FundingCreate.route) {
            FundingCreateScreen(navController, fundingCreateViewModel)
        }

        // 펀딩 입금
        composable(NavRoutes.FundingDeposit.route) {
            FundingDepositScreen(navController, fundingDetailViewModel, accountViewModel)
        }

        composable(NavRoutes.FundingSubmitForm.route) {
            SubmitFormScreen(navController, fundingDetailViewModel)
        }
    }
}
