package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a702.finafan.presentation.account.AccountCodeConfirmScreen
import com.a702.finafan.presentation.account.AccountCodeScreen
import com.a702.finafan.presentation.account.AccountInputScreen
import com.a702.finafan.presentation.account.AccountSendScreen
import com.a702.finafan.presentation.account.ConnectAccountScreen
import com.a702.finafan.presentation.account.ConnectBankScreen
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel
import com.a702.finafan.presentation.main.MainScreen
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

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main.route
    ) {
        composable(NavRoutes.Main.route) {
            MainScreen(navController, modifier)
        }

        composable(NavRoutes.Chat.route) {
            val chatViewModel: ChatViewModel = hiltViewModel()
            ChatScreen(viewModel = chatViewModel)
        }

        composable(NavRoutes.SavingMain.route) {
            SavingMainScreen()
        }

        composable(NavRoutes.SavingDeposit.route) {
            SavingDepositScreen()
        }
        composable(NavRoutes.SavingDesc.route) {
            SavingDescScreen()
        }
        composable(NavRoutes.SavingAccountInfo.route) {
            SavingAccountInfoScreen()
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