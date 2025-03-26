package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel
import com.a702.finafan.presentation.main.MainScreen

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
            SavingNavGraph(navController = navController)
        }

        composable(NavRoutes.Account.route) {
            AccountNavGraph(navController)
        }

    }
}