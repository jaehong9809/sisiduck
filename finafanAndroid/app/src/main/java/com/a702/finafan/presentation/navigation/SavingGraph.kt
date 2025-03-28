package com.a702.finafan.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.a702.finafan.domain.savings.model.SavingCreate
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
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
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

fun NavGraphBuilder.savingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Saving.route
    ) {
        composable(NavRoutes.SavingMain.route + "/{savingAccountId}") { backStackEntry ->
            val savingViewModel: SavingViewModel = hiltViewModel()
            val savingAccountId = backStackEntry.arguments?.getLong("savingAccountId")

            SavingMainScreen(savingViewModel, savingAccountId)
        }

        composable(NavRoutes.TransactionDetail.route) { backStackEntry ->
            val transaction = navController.previousBackStackEntry?.savedStateHandle?.get<Transaction>("transaction")

            TransactionDetailScreen(
                onNavigateClick = { navController.popBackStack() },
                transaction ?: Transaction()
            )
        }

        composable(NavRoutes.SavingDeposit.route) {
            SavingDepositScreen(onComplete = {
                navController.navigate(NavRoutes.SavingMain.route) {
                    launchSingleTop = true
                }
            })
        }

        composable(NavRoutes.SavingDesc.route) {
            SavingDescScreen()
        }

        composable(NavRoutes.StarSearch.route) {
            val savingViewModel: SavingViewModel = hiltViewModel()

            StarSearchScreen(
                onSelect = { selectStar ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectStar", selectStar)
                    navController.navigate(NavRoutes.SavingNameInput.route)
                },
                savingViewModel
            )
        }

        composable(NavRoutes.SavingNameInput.route) { backStackEntry ->
            val star = navController.previousBackStackEntry?.savedStateHandle?.get<Star>("selectStar")

            SavingNameInputScreen(
                star = star ?: Star(),
                onComplete = { savingCreate ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("savingCreate", savingCreate)
                    navController.navigate(NavRoutes.SavingSelectAccount.route)
                }
            )
        }

        composable(NavRoutes.SavingSelectAccount.route) { backStackEntry ->
            val savingCreate = navController.currentBackStackEntry?.savedStateHandle?.get<SavingCreate>("savingCreate")

            SavingSelectAccountScreen(
                savingCreate = savingCreate ?: SavingCreate(),
                onComplete = { savingId ->
                    navController.navigate(NavRoutes.SavingMain.route + "/${savingId}") {
                        popUpTo(NavRoutes.SavingDesc.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
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

    }
}