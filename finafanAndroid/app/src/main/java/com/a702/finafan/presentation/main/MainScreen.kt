package com.a702.finafan.presentation.main

import android.Manifest
import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ImageItem
import com.a702.finafan.common.ui.component.MainSquareIconButton
import com.a702.finafan.common.ui.component.MainWideButton
import com.a702.finafan.common.ui.component.MainWideIconButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainBtnLightBlue
import com.a702.finafan.common.ui.theme.MainBtnLightOrange
import com.a702.finafan.common.ui.theme.MainBtnLightYellow
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.infrastructure.android.BleForegroundService
import com.a702.finafan.presentation.ble.rememberBlePermissionLauncher
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchMainSavings()
//        viewModel.fetchMainRanking(RankingType.DAILY)
    }

    val mainSavingState by viewModel.mainSavingState.collectAsState()
    val mainRankingState by viewModel.mainRankingState.collectAsState()

    val context = LocalContext.current

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

    Column(modifier = modifier) {
        Row {
            MainSquareIconButton(
                onClick = {
                    val accountId = 2
                    navController.navigate(NavRoutes.SavingMain.route + "/${accountId}")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = MainBlack,
                        modifier = Modifier.size(48.dp)
                    )
                },
                text = "적금"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MainSquareIconButton(
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

        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            MainSquareIconButton(
                onClick = {
                    navController.navigate(NavRoutes.SavingDeposit.route)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = MainBlack,
                        modifier = Modifier.size(48.dp)
                    )
                },
                text = "입금하기"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            MainWideIconButton(
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

            Text("이 아래부터는 제거해야 합니다...", fontSize = 30.sp)
            Row() {
                MainSquareIconButton(
                    onClick = {
                        // TODO: 적금계좌 고유번호 pk 넘기기
                        navController.navigate(NavRoutes.SavingMain.route + "/11")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MainBlack,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    text = "적금"
                )

                MainSquareIconButton(
                    onClick = {
                        navController.navigate(NavRoutes.SavingDesc.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MainBlack,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    text = "적금 가입"
                )
            }

        }
    }
}