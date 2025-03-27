package com.a702.finafan.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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

fun NavGraphBuilder.savingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Saving.route
    ) {
        composable(NavRoutes.SavingMain.route) {
            SavingMainScreen()
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
            StarSearchScreen()
            // TODO: 선택하기 -> 이전화면에 선택된 스타의 이름이 보여야함 (Star 객체를 보내야 함)
        }
        composable(NavRoutes.StarSelect.route) {
            StarSelectScreen()
            // TODO: 검색하기 -> star search로 넘어가기
        }

        composable(NavRoutes.SavingNameInput.route) {
            SavingNameInputScreen("test")
            // TODO: 다음 -> SelectAccount로 이동, 입력된 이름도 같이 보내기
        }

        composable(NavRoutes.SavingSelectAccount.route) {
            SavingSelectAccountScreen()
            // TODO: 가입하기 -> 적금 계좌 개설 후 SavingMain 화면으로 이동 (이전의 화면은 사라짐 popUpTo 사용)
        }

        composable(NavRoutes.TransactionDetail.route) {
            TransactionDetailScreen(
                onNavigateClick = { navController.popBackStack() },
                transaction = Transaction(true, "test", 44444, 64444, "2025년 3월 24일 17:07", "")
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
            SavingCancelScreen()
            // TODO: 해지하기 -> 해지 API 호출 후 확인 다이얼로그 띄우기 -> 메인으로 이동 (popUpTo 사용)
        }

    }
}