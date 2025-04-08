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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel

// 은행 선택 화면
@Composable
fun ConnectBankScreen(
    viewModel: AccountViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val selectedBankIds = rememberSaveable { mutableStateListOf<Long>() }
    val accountState by viewModel.accountState.collectAsState()

    // 은행 목록
    LaunchedEffect(Unit) {
        viewModel.fetchBankList()
    }

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
                    // 은행 선택
                    viewModel.selectBank(selectedBankIds)
                    onComplete()
                },
                text = stringResource(R.string.btn_next),
                isEnabled = selectedBankIds.isNotEmpty()
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
                    text = stringResource(R.string.connect_account_select_back_title),
                    color = MainBlack,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 34.dp, bottom = 12.dp),
                    text = stringResource(R.string.select_bank_label),
                    color = MainBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Start
                )
            }

            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                if (accountState.isLoading) {
                    item {
                        CommonProgress()
                    }
                } else {

                    items(accountState.bankList.size) { index ->
                        val bank = accountState.bankList[index]
                        val isSelected = selectedBankIds.any { it == bank.bankId }

                        BankItem(
                            bank = bank,
                            selectedBanks = selectedBankIds,
                            onToggleSelect = { clickedBankId ->
                                if (isSelected) {
                                    selectedBankIds.removeAll { it == clickedBankId }
                                } else {
                                    selectedBankIds.add(clickedBankId)
                                }
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun ConnectBankPreview() {
    val bankList = mutableListOf("NH농협", "우리은행", "하나은행", "국민은행", "신한은행", "카카오뱅크", "토스", "기업은행")
    ConnectBankScreen(onComplete = {})
}