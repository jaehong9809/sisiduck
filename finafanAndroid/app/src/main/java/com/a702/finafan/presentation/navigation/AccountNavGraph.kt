package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a702.finafan.presentation.account.AccountCodeConfirmScreen
import com.a702.finafan.presentation.account.AccountCodeScreen
import com.a702.finafan.presentation.account.AccountInputScreen
import com.a702.finafan.presentation.account.AccountSendScreen
import com.a702.finafan.presentation.account.ConnectAccountScreen
import com.a702.finafan.presentation.account.ConnectBankScreen

@Composable
fun AccountNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main.route
    ) {

        composable(NavRoutes.ConnectBank.route) {
            ConnectBankScreen(mutableListOf())
        }
        composable(NavRoutes.AccountInput.route) {
            AccountInputScreen("NH농협")
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