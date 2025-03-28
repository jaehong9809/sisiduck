package com.a702.finafan.presentation.main

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ImageItem
import com.a702.finafan.common.ui.component.MainSquareIconButton
import com.a702.finafan.common.ui.component.MainWideIconButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.infrastructure.android.BleForegroundService
import com.a702.finafan.presentation.ble.rememberBlePermissionLauncher
import com.a702.finafan.presentation.navigation.NavRoutes

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

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

            Spacer(modifier = Modifier.padding(8.dp))

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

        Spacer(modifier = Modifier.padding(8.dp))

        MainSquareIconButton(
            onClick = {
                navController.navigate(NavRoutes.FundingMain.route)
            },
            icon = {
                ImageItem(Modifier.padding(5.dp), { }, R.drawable.funding_box)
            },
            text = "모금"
        )

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
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
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

            MainSquareIconButton(
                onClick = {
                    navController.navigate(NavRoutes.SavingAccountInfo.route)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = MainBlack,
                        modifier = Modifier.size(48.dp)
                    )
                },
                text = "적금 계좌 정보"
            )

            Spacer(modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.padding(8.dp))
        
        MainSquareIconButton(
            onClick = {
                navController.navigate(NavRoutes.Account.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "1원 인증"
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MainWideIconButton(
            onClick = {
                navController.navigate(NavRoutes.Chat.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "덕순이"
        )
    }
}