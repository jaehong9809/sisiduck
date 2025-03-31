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
import com.a702.finafan.presentation.savings.TermGuideScreen
import com.a702.finafan.presentation.savings.TransactionDetailScreen
import com.a702.finafan.presentation.savings.ranking.RankingHistoryScreen
import com.a702.finafan.presentation.savings.ranking.RankingScreen
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

fun NavGraphBuilder.savingGraph(
    navController: NavHostController,
    savingViewModel: SavingViewModel
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Saving.route
    ) {

        composable(NavRoutes.SavingMain.route + "/{accountId}") { backStackEntry ->
            val savingAccountId = backStackEntry.arguments?.getString("accountId")!!.toLongOrNull()

            SavingMainScreen(savingViewModel, savingAccountId ?: 0)
        }

        composable(NavRoutes.TransactionDetail.route) { backStackEntry ->
            TransactionDetailScreen(
                savingViewModel,
                onNavigateClick = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.SavingDeposit.route + "/{accountId}") { backStackEntry ->
            val savingAccountId = backStackEntry.arguments?.getString("accountId")!!.toLongOrNull()

            SavingDepositScreen(
                savingViewModel,
                savingAccountId ?: 0
            )
        }

        composable(NavRoutes.SavingDesc.route) {
            SavingDescScreen()
        }

        composable(NavRoutes.StarSearch.route) {
            StarSearchScreen(
                onSelect = {
                    navController.navigate(NavRoutes.SavingNameInput.route)
                },
                savingViewModel
            )
        }

        composable(NavRoutes.SavingNameInput.route) {
            SavingNameInputScreen(
                savingViewModel,
                onComplete = {
                    navController.navigate(NavRoutes.SavingSelectAccount.route)
                }
            )
        }

        composable(NavRoutes.SavingSelectAccount.route) {
            SavingSelectAccountScreen(
                savingViewModel
            )
        }

        composable(NavRoutes.TermGuide.route + "/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            TermGuideScreen(title, onConfirm = { navController.popBackStack() })
        }

        composable(NavRoutes.SavingAccountInfo.route) {
            SavingAccountInfoScreen(
                onCancelClick = {
                    navController.navigate(NavRoutes.SavingCancel.route)
                }
            )
        }

        composable(NavRoutes.SavingCancel.route) {
            SavingCancelScreen(onComplete = {
                navController.navigate(NavRoutes.Main.route) {
                    popUpTo(NavRoutes.Main.route)
                }
            })
        }

        composable(NavRoutes.RankingMain.route) {
            RankingScreen(savingViewModel)
        }

        composable(NavRoutes.RankingHistory.route) { backStackEntry ->
            RankingHistoryScreen(
                viewModel = savingViewModel
            )
        }

    }
}