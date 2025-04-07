package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.a702.finafan.presentation.auth.LoginScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.ble.UuidListScreen
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel
import com.a702.finafan.presentation.main.MainScreen
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavControllerProvider(navController = navController) {
        val savingViewModel: SavingViewModel = hiltViewModel()

        val systemUiController = rememberSystemUiController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        val statusBarColor = when (currentRoute) {
            NavRoutes.Main.route, NavRoutes.AllAccountParam.route, NavRoutes.FundingMain.route -> MainBgLightGray
            else -> MainWhite
        }

        SideEffect {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = true
            )
        }

        NavHost(
            navController = navController,
            startDestination = NavRoutes.Main.route
        ) {

            composable(NavRoutes.Main.route) {
                MainScreen(navController, modifier)
            }

            composable(NavRoutes.Login.route) {
                LoginScreen()
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
