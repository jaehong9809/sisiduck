package com.a702.finafan.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.account.AccountCodeConfirmScreen
import com.a702.finafan.presentation.account.AccountCodeScreen
import com.a702.finafan.presentation.account.AccountInputScreen
import com.a702.finafan.presentation.account.AccountSendScreen
import com.a702.finafan.presentation.account.ConnectAccountScreen
import com.a702.finafan.presentation.account.ConnectBankScreen
import com.google.gson.Gson

fun NavGraphBuilder.accountGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.ConnectBank.route, route = NavRoutes.Account.route
    ) {
        composable(NavRoutes.ConnectBank.route) {
            ConnectBankScreen(onSelect = { selectBank ->
                val bankJson = Gson().toJson(selectBank)
                navController.navigate(NavRoutes.AccountInput.route + "/${bankJson}")
            })
        }

        composable(NavRoutes.AccountInput.route + "/{selectBank}") { backStackEntry ->
            val bankJson = backStackEntry.arguments?.getString("selectBank")
            val bank = Gson().fromJson(bankJson, Bank::class.java)

            AccountInputScreen(bank)
        }

        composable(NavRoutes.AccountSend.route) {
            AccountSendScreen("NH농협")
        }

        composable(NavRoutes.AccountCode.route) {
            AccountCodeScreen("NH농협")
        }

        composable(NavRoutes.AccountCodeConfirm.route) {
            AccountCodeConfirmScreen("NH농협")
        }

        composable(NavRoutes.ConnectAccount.route) {
            ConnectAccountScreen("NH농협")
        }

    }
}