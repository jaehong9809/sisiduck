package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a702.finafan.presentation.ble.UuidListScreen
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel
import com.a702.finafan.presentation.main.MainScreen
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavControllerProvider(navController = navController) {
        val savingViewModel: SavingViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = NavRoutes.Main.route
        ) {
            composable(NavRoutes.Main.route) {
                MainScreen(navController, modifier)
            }

            composable(NavRoutes.Login.route) {
            }

            composable(NavRoutes.Chat.route) {
                val chatViewModel: ChatViewModel = hiltViewModel()
                ChatScreen(viewModel = chatViewModel)
            }

            composable(NavRoutes.Ble.route){
                UuidListScreen()
            }

            savingGraph(navController = navController, savingViewModel = savingViewModel)

            accountGraph(navController = navController, savingViewModel = savingViewModel)

            fundingGraph(navController)

        }
    }

}
