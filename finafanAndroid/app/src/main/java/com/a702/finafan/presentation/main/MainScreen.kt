package com.a702.finafan.presentation.main

import android.Manifest
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.ui.component.ImageItem
import com.a702.finafan.common.ui.component.MainSquareIconButton
import com.a702.finafan.common.ui.component.MainWideButton
import com.a702.finafan.common.ui.component.MainWideIconButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBtnLightBlue
import com.a702.finafan.common.ui.theme.MainBtnLightOrange
import com.a702.finafan.common.ui.theme.MainBtnLightYellow
import com.a702.finafan.infrastructure.android.BleForegroundService
import com.a702.finafan.presentation.ble.rememberBlePermissionLauncher
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val mainSavingState by viewModel.mainSavingState.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val userState by viewModel.userState.collectAsState()

    val blePermissionLauncher = rememberBlePermissionLauncher(
        onGranted = {
            val intent = Intent(context, BleForegroundService::class.java)
            ContextCompat.startForegroundService(context, intent)
            navController.navigate(NavRoutes.Ble.route)
        },
        onDenied = {
            Toast.makeText(context, "권한이 거부되어 기능을 사용할 수 없습니다.", Toast.LENGTH_LONG).show()
        }
    )

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            viewModel.fetchUserInfo()
            viewModel.fetchMainSavings()
        }
    }

    // Logging userState
    LaunchedEffect(userState) {
        Log.d("Compose", "userState changed: $userState")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MainBgLightGray)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (val state = userState) {
                    is DataResource.Success -> "${state.data.userName}님"
                    else -> "로그인이 필요합니다"
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 20.dp, top = 30.dp, bottom = 6.dp),
                textAlign = TextAlign.Left,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            CardCarousel(
                isLoggedIn = isLoggedIn,
                savings = mainSavingState.savings,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                navController = navController
            )

            MainWideButton(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    navController.navigate(NavRoutes.AllAccount.route)
                },
                text = stringResource(R.string.all_acount_button)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            MainSquareIconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    blePermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.BLUETOOTH_ADVERTISE,
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_CONNECT,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MainBtnLightBlue)
                            .padding(5.dp)
                    ) {
                        ImageItem(Modifier.fillMaxSize(), { }, R.drawable.ble)
                    }
                },
                text = "주변 팬 찾기"
            )

            MainSquareIconButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    navController.navigate(NavRoutes.FundingMain.route)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MainBtnLightOrange)
                            .padding(10.dp)
                    ) {
                        ImageItem(Modifier.fillMaxSize(), { }, R.drawable.funding_box)
                    }
                },
                text = "모금"
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        MainWideIconButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                navController.navigate(NavRoutes.Chat.route)
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MainBtnLightYellow)
                        .padding(8.dp)
                ) {
                    ImageItem(Modifier.fillMaxSize(), { }, R.drawable.duck)
                }
            },
            text = "덕순이랑 놀기"
        )

        Text(
            text = "스타별 적금 랭킹",
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp, top = 40.dp, bottom = 20.dp),
            textAlign = TextAlign.Left,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold
        )

        MainRanking(viewModel, modifier = Modifier.padding(horizontal = 16.dp))
    }
}
