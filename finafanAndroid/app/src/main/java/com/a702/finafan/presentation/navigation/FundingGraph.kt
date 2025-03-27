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

        composable(NavRoutes.FundingDetail.route + "/{fundingId}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getString("fundingId")?.toIntOrNull() ?: 0

            val myStars = getMyStars()
            val star = myStars.find { it.id == fundingId } ?: myStars.firstOrNull()

            if (star == null) {
                Log.e("FundingDetail", "No stars available! fundingId: $fundingId")
                return@composable
            }

            FundingDetailScreen(star, fundingId)
        }

        composable(NavRoutes.FundingJoin.route + "/{fundingId}") { backStackEntry ->
            val fundingId = backStackEntry.arguments?.getString("fundingId")!!.toInt()
            FundingTermScreen(navController, fundingId)
        }

    }

}