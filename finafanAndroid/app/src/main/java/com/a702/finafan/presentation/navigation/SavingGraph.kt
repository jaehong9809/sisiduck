package com.a702.finafan.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.a702.finafan.presentation.savings.SavingAccountInfoScreen
import com.a702.finafan.presentation.savings.SavingCancelScreen
import com.a702.finafan.presentation.savings.SavingDepositScreen
import com.a702.finafan.presentation.savings.SavingDescScreen
import com.a702.finafan.presentation.savings.SavingMainScreen
import com.a702.finafan.presentation.savings.SavingNameInputScreen
import com.a702.finafan.presentation.savings.SavingSelectAccountScreen
import com.a702.finafan.presentation.savings.StarSearchScreen
import com.a702.finafan.presentation.savings.StarSelectScreen
import com.a702.finafan.presentation.savings.TermGuideScreen
import com.a702.finafan.presentation.savings.Transaction
import com.a702.finafan.presentation.savings.TransactionDetailScreen

fun NavGraphBuilder.savingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Saving.route
    ) {
        composable(NavRoutes.SavingMain.route) {
            SavingMainScreen(
//                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.SavingDeposit.route) {
            SavingDepositScreen(
//                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.SavingDesc.route) {
            SavingDescScreen(
//                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.SavingAccountInfo.route) {
            SavingAccountInfoScreen(

            )
        }
        composable(NavRoutes.SavingCancel.route) {
            SavingCancelScreen()
        }
        composable(NavRoutes.SavingNameInput.route) {
            SavingNameInputScreen("test")
        }
        composable(NavRoutes.SavingSelectAccount.route) {
            SavingSelectAccountScreen()
        }

        composable(NavRoutes.TransactionDetail.route) {
            TransactionDetailScreen(
                transaction = Transaction(true, "test", 44444, 64444, "2025년 3월 24일 17:07", "")
            )
        }

        composable(NavRoutes.TermGuide.route) {
            TermGuideScreen()
        }

        composable(NavRoutes.StarSearch.route) {
            StarSearchScreen()
        }
        composable(NavRoutes.StarSelect.route) {
            StarSelectScreen()
        }

    }
}