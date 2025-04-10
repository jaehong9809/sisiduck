package com.a702.finafan.presentation.ble

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.CustomTypography.displayLarge
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.ble.model.FanDeposit

@Composable
fun MatchedFanDepositScreen(
    viewModel: BleFanRadarViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val deposits = uiState.fanDeposits

    LaunchedEffect(Unit) {
        viewModel.fetchMatchedFanDeposits()
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                text = "응원 적금 내역",
                isTextCentered = true
            )
        },
        containerColor = MainWhite
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            if (deposits.isEmpty()) {
                Text(
                    text = "아직 응원 적금이 없어요.",
                    style = displayLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 100.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(deposits) { deposit ->
                        FanDepositCard(deposit)
                    }
                }
            }
        }
    }
}

@Composable
fun FanDepositCard(deposit: FanDeposit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "닉네임: ${deposit.userName}", fontWeight = FontWeight.Bold)
            Text(text = "응원 내역: ${deposit.message}")
            Text(text = "적금액: ${deposit.amount}원")
        }
    }
}

