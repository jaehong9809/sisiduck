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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.component.ConfirmDialog
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

    val context = LocalContext.current

    val selectedAccountNos = remember { mutableStateListOf<String>() }
    val savingState by viewModel.savingState.collectAsState()

    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = savingState.error?.message.toString()

            viewModel.clearError()
        }
    }

    LaunchedEffect(savingState.isConnect) {
        if (savingState.isConnect) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_connect_account_complete)
        }
    }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                if (savingState.isConnect) {
                    viewModel.resetConnectState()
                    onComplete()
                }
            }
        )
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
                    // 계좌 선택
                    viewModel.selectAccount(selectedAccountNos)
                },
                text = stringResource(R.string.btn_next),
                isEnabled = selectedAccountNos.isNotEmpty()
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
                    items(savingState.accounts) { item ->
                        val account = item
                        val isSelected = selectedAccountNos.any { it == account.accountNo }

                        AccountInfoItem(
                            account = account,
                            isConnect = true,
                            selectedAccounts = selectedAccountNos,
                            onSelect = { clickedAccountNo ->
                                if (isSelected) {
                                    selectedAccountNos.removeAll { it == clickedAccountNo }
                                } else {
                                    selectedAccountNos.add(clickedAccountNo)
                                }
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }

    }
}