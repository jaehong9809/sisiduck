package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.a702.finafan.presentation.account.AccountCodeConfirmScreen
import com.a702.finafan.presentation.account.AccountCodeScreen
import com.a702.finafan.presentation.account.AccountInputScreen
import com.a702.finafan.presentation.account.AccountSendScreen
import com.a702.finafan.presentation.account.AllAccountScreen
import com.a702.finafan.presentation.account.ConnectAccountScreen
import com.a702.finafan.presentation.account.ConnectBankScreen
import com.a702.finafan.presentation.account.SelectBankAccountScreen
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

fun NavGraphBuilder.accountGraph(
    savingViewModel: SavingViewModel,
    accountViewModel: AccountViewModel,
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.ConnectBank.route, route = NavRoutes.Account.route
    ) {
        composable(NavRoutes.ConnectBank.route) {
            ConnectBankScreen(accountViewModel, onComplete = {
                navController.navigate(NavRoutes.SelectAccount.route)
            })
        }

        composable(NavRoutes.SelectAccount.route) {
            SelectBankAccountScreen(accountViewModel, onComplete = {
                navController.navigate(NavRoutes.AllAccount.route + "?selectedTabIndex=2") {
                    popUpTo(NavRoutes.AllAccount.route)
                }
            })
        }

        composable(NavRoutes.AccountInput.route) {
            AccountInputScreen(accountViewModel, onComplete = {
                    navController.navigate(NavRoutes.AccountSend.route)
                })
        }

        composable(NavRoutes.AccountSend.route) {
            AccountSendScreen(accountViewModel, onComplete = {
                navController.navigate(NavRoutes.AccountCode.route)
            })
        }

        composable(NavRoutes.AccountCode.route) {
            AccountCodeScreen(accountViewModel,
                onComplete = {
                    navController.navigate(NavRoutes.AccountCodeConfirm.route)
                },
                onNavigateClick = {
                    navController.navigate(NavRoutes.ConnectBank.route) {
                    popUpTo(NavRoutes.ConnectBank.route)
                }
            })
        }

        composable(NavRoutes.AccountCodeConfirm.route) {
            AccountCodeConfirmScreen(accountViewModel)
        }

        composable(NavRoutes.ConnectAccount.route) {
            ConnectAccountScreen(
                accountViewModel,
                onComplete = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            NavRoutes.AllAccount.route + "?selectedTabIndex={selectedTabIndex}",
            arguments = listOf(navArgument("selectedTabIndex") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val selectedTabIndex = rememberSaveable  {
                mutableIntStateOf(
                    backStackEntry.arguments?.getInt("selectedTabIndex") ?: 0
                )
            }

            AllAccountScreen(selectedTabIndex, savingViewModel, accountViewModel)
        }

    }
}