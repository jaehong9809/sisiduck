package com.a702.finafan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.auth.LoginScreen
import com.a702.finafan.presentation.ble.BleFanRadarScreen
import com.a702.finafan.presentation.ble.FanRadarScreen
import com.a702.finafan.presentation.ble.MatchedFanDepositScreen
import com.a702.finafan.presentation.ble.UuidListScreen
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingCreateViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingDetailViewModel
import com.a702.finafan.presentation.funding.viewmodel.FundingViewModel
import com.a702.finafan.presentation.main.MainScreen
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavControllerProvider(navController = navController) {
        val mainViewModel: MainViewModel = hiltViewModel()

        val savingViewModel: SavingViewModel = hiltViewModel()
        val accountViewModel: AccountViewModel = hiltViewModel()

        val fundingViewModel: FundingViewModel = hiltViewModel()
        val fundingDetailViewModel: FundingDetailViewModel = hiltViewModel()
        val fundingCreateViewModel: FundingCreateViewModel = hiltViewModel()

        val systemUiController = rememberSystemUiController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        val statusBarColor = when (currentRoute) {
            NavRoutes.Main.route, NavRoutes.AllAccountParam.route, NavRoutes.FundingMain.route, NavRoutes.Chat.route -> MainBgLightGray
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
                LoginScreen(navController)
            }

            composable(NavRoutes.Chat.route) {
                val chatViewModel: ChatViewModel = hiltViewModel()
                ChatScreen(viewModel = chatViewModel)
            }

            composable(NavRoutes.Ble.route){
                BleFanRadarScreen(navController)
            }

            composable("matched_fan_deposits") {
                MatchedFanDepositScreen(navController = navController)
            }

            savingGraph(
                navController = navController,
                savingViewModel = savingViewModel,
                accountViewModel = accountViewModel
            )

            accountGraph(
                navController = navController,
                savingViewModel = savingViewModel,
                fundingViewModel = fundingViewModel,
                accountViewModel = accountViewModel
            )

            fundingGraph(
                navController = navController,
                mainViewModel = mainViewModel,
                fundingViewModel = fundingViewModel,
                fundingDetailViewModel = fundingDetailViewModel,
                fundingCreateViewModel = fundingCreateViewModel,
                accountViewModel = accountViewModel
            )

        }
    }

}
