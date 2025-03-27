package com.a702.finafan.presentation.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.a702.finafan.presentation.funding.FundingDetailScreen
import com.a702.finafan.presentation.funding.FundingScreen
import com.a702.finafan.presentation.funding.FundingTermScreen
import com.a702.finafan.presentation.funding.getMyStars

fun NavGraphBuilder.fundingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Funding.route
    ) {
        composable(NavRoutes.FundingMain.route) {
            FundingScreen(navController)
        }

        // TODO: 상세 조회로 넘어갈 때 API에 펀딩 제목이 없어서 내려주기로 했음, 받는대로 고칠 것
        composable(NavRoutes.FundingDetail.route + "/{fundingId}/{fundingTitle}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getLong("fundingId") ?: 0
            val fundingTitle = backStackEntry.arguments?.getString("fundingTitle") ?: "모금"

            val myStars = getMyStars()
            val star = myStars.find { it.id == fundingId } ?: myStars.firstOrNull()
            FundingDetailScreen(star, fundingId, fundingTitle)
        }

        composable(NavRoutes.FundingJoin.route + "/{fundingId}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getString("fundingId")!!.toLong()
            FundingTermScreen(navController, fundingId)
        }

    }

}