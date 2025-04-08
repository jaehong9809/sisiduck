package com.a702.finafan.presentation.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.presentation.navigation.NavRoutes


@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = loginViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                textOnClick = null,
                text = stringResource(R.string.login),
                isTextCentered = true
            )
        },
        bottomBar = {
            PrimaryGradBottomButton(
                modifier = Modifier,
                onClick = {
                    loginViewModel.onLoginClicked()
                },
                text = stringResource(R.string.login)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = 18.sp,
                text = stringResource(R.string.ssafy_login)
            )
        }
    }

    uiState.value.openOAuthUrl?.let { url ->
        LaunchedEffect(url) {
            val noCacheUrl = "$url&t=${System.currentTimeMillis()}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(noCacheUrl))
            context.startActivity(intent)
            Log.d("Try to Open URL: ", url)
        }
    }

    if (uiState.value.isLoggedIn) {
        LaunchedEffect(Unit) {
            Log.d("LoginScreen", "✅ Login 성공, 메인으로 이동합니다!")
            navController.navigate(NavRoutes.Main.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

}
