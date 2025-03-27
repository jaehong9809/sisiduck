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
import com.a702.finafan.presentation.savings.Star
import com.a702.finafan.presentation.savings.StarSearchScreen
import com.a702.finafan.presentation.savings.TermGuideScreen
import com.a702.finafan.presentation.savings.Transaction
import com.a702.finafan.presentation.savings.TransactionDetailScreen
import com.google.gson.Gson

fun NavGraphBuilder.savingGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = NavRoutes.Main.route, route = NavRoutes.Saving.route
    ) {
        composable(NavRoutes.SavingMain.route + "/{savingId}") { backStackEntry ->
            val savingId = backStackEntry.arguments?.getInt("savingId")
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
            StarSearchScreen(onSelect = { selectStar ->
                val starJson = Gson().toJson(selectStar)
                navController.navigate(NavRoutes.SavingNameInput.route + "/${starJson}")
            })
        }

        composable(NavRoutes.SavingNameInput.route + "/{star}") { backStackEntry ->
            val starJson = backStackEntry.arguments?.getString("star")
            val star = Gson().fromJson(starJson, Star::class.java)

            SavingNameInputScreen(
                selectStar = star,
                onComplete = { savingName ->
                    navController.navigate(NavRoutes.SavingSelectAccount.route + "/${starJson}/${savingName}")
                }
            )
        }

        composable(NavRoutes.SavingSelectAccount.route + "/{star}/{name}") { backStackEntry ->
            val starJson = backStackEntry.arguments?.getString("star")
            val star = Gson().fromJson(starJson, Star::class.java)
            val name = backStackEntry.arguments?.getString("title")

            SavingSelectAccountScreen(star, name, onComplete = { savingId ->
                navController.navigate(NavRoutes.SavingMain.route + "/${savingId}") {
                    popUpTo(NavRoutes.SavingDesc.route) { inclusive = true }
                    launchSingleTop = true
                }
            })
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