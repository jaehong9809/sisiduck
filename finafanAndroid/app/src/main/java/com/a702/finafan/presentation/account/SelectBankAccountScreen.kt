package com.a702.finafan.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 계좌 선택 화면
@Composable
fun SelectBankAccountScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val selectedAccountIds = remember { mutableStateListOf<Long>() }
    val savingState by viewModel.savingState.collectAsState()

    Scaffold(
        topBar = {
            CommonBackTopBar(modifier = Modifier)
        },
        bottomBar = {
            PrimaryGradBottomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                onClick = {
                    // TODO: 다음 누르면 계좌 선택 API 호출 -> 다이얼로그 띄우고 연결 계좌 목록 화면으로 이동하기
                    onComplete()
                },
                text = stringResource(R.string.btn_next),
                isEnabled = selectedAccountIds.isNotEmpty()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainWhite)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.connect_account_select_account_title),
                    color = MainBlack,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 34.dp, bottom = 12.dp),
                    text = stringResource(R.string.select_account_label),
                    color = MainBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Start
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                if (savingState.isLoading) {
                    item {
                        CommonProgress()
                    }
                } else {
                    items(savingState.withdrawalAccounts) { item ->
                        val account = item
                        val isSelected = selectedAccountIds.any { it == account.accountId }

                        AccountInfoItem(
                            account = account,
                            isConnect = true,
                            selectedAccounts = selectedAccountIds,
                            onSelect = { clickedAccountId ->
                                if (isSelected) {
                                    selectedAccountIds.removeAll { it == clickedAccountId }
                                } else {
                                    selectedAccountIds.add(clickedAccountId)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }

    }
}