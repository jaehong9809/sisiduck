package com.a702.finafan.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.account.AccountCodeConfirmScreen
import com.a702.finafan.presentation.account.AccountCodeScreen
import com.a702.finafan.presentation.account.AccountInputScreen
import com.a702.finafan.presentation.account.AccountSendScreen
import com.a702.finafan.presentation.account.ConnectAccountScreen
import com.a702.finafan.presentation.account.ConnectBankScreen

fun NavGraphBuilder.accountGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.ConnectBank.route, route = NavRoutes.Account.route
    ) {
        composable(NavRoutes.ConnectBank.route) {
            ConnectBankScreen(onSelect = { selectBank ->
                navController.currentBackStackEntry?.savedStateHandle?.set("bank", selectBank)
                navController.navigate(NavRoutes.AccountInput.route)
            })
        }

        composable(NavRoutes.AccountInput.route) { backStackEntry ->
            val bank = navController.previousBackStackEntry?.savedStateHandle?.get<Bank>("bank")

            AccountInputScreen(
                bank ?: Bank(), onComplete = { account ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("account", account)
                    navController.navigate(NavRoutes.AccountSend.route)
                })
        }

        composable(NavRoutes.AccountSend.route) {
            val account = navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")

            AccountSendScreen(account ?: Account(), onComplete = { account ->
                navController.currentBackStackEntry?.savedStateHandle?.set("account", account)
                navController.navigate(NavRoutes.AccountCode.route)
            })
        }

        composable(NavRoutes.AccountCode.route) {
            val account = navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")

            AccountCodeScreen(account ?: Account(),
                onComplete = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("account", account)
                    navController.navigate(NavRoutes.AccountCodeConfirm.route)
                },
                onNavigateClick = {
                    navController.navigate(NavRoutes.ConnectBank.route) {
                    popUpTo(NavRoutes.ConnectBank.route)
                }
            })
        }

        composable(NavRoutes.AccountCodeConfirm.route) {
            val account = navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")

            AccountCodeConfirmScreen(account ?: Account())
        }

        composable(NavRoutes.ConnectAccount.route) {
            val account = navController.previousBackStackEntry?.savedStateHandle?.get<Account>("account")

            ConnectAccountScreen(account ?: Account())
        }

    }
}