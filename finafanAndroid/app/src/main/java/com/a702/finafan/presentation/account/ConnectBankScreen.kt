package com.a702.finafan.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 은행 선택 화면
@Composable
fun ConnectBankScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val selectBank = remember { mutableStateOf(Bank()) }
    val savingState by viewModel.savingState.collectAsState()

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
                    viewModel.updateBank(selectBank.value)
                    onComplete()
                },
                text = stringResource(R.string.btn_next),
                isEnabled = selectBank.value.bankCode.isNotEmpty()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainWhite)
                .fillMaxSize()
                .imePadding()
                .windowInsetsPadding(WindowInsets.ime)
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
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(savingState.bankList.size) { index ->
                    val bank = savingState.bankList[index]
                    val isSelected = selectBank.value.bankCode == bank.bankCode

                    BankItem(
                        bank = bank,
                        isSelected = isSelected,
                        onSelect = {
                            selectBank.value = it
                        }
                    )
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