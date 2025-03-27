package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.MainSquareIconButton
import com.a702.finafan.common.ui.theme.MainBlack


@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainSquareIconButton(
            onClick = { navController.navigate("chat") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "AI 챗봇 덕순이",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "덕순이와 대화"
        )

        Spacer(modifier = Modifier.height(16.dp))

        MainSquareIconButton(
            onClick = { navController.navigate("ble") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "BLE로 주변 팬 찾기",
                    tint = MainBlack,
                    modifier = Modifier.size(48.dp)
                )
            },
            text = "주변 팬 찾기"
        )
    }
}
