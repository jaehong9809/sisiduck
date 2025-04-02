package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.savings.AccountInfoItem
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 연결 계좌 확인 화면
@Composable
fun ConnectAccountScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val context = LocalContext.current

    val savingState by viewModel.savingState.collectAsState()
    val account = savingState.connectAccount

    val showDeleteDialog = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val dialogContent = remember { mutableStateOf("") }

    if (showDeleteDialog.value) {
        ConfirmDialog(
            showDeleteDialog,
            isConfirm = false,
            content = context.getString(R.string.saving_item_connect_cancel_confirm),
            onClickConfirm = {
                viewModel.deleteConnectAccount(account.accountId)
            }
        )
    }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                if (savingState.isCancel) {
                    onComplete()
                }
            }
        )
    }

    LaunchedEffect(savingState.isCancel) {
        if (savingState.isCancel) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_connect_cancel_complete)
        }
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = savingState.error?.message.toString()

            viewModel.clearError()
        }
    }

    ConnectAccountLayout (
        topBarTitle = stringResource(R.string.saving_account_connect_bank_title),
        title = stringResource(R.string.connect_account_confirm_title),
        buttonText = stringResource(R.string.btn_delete),
        isButtonEnabled = true,
        onButtonClick = {
            // 연결 계좌 삭제 다이얼로그
            showDeleteDialog.value = true
        }
    ) {

        Column {
            AccountInfoItem(
                modifier = Modifier.padding(top = 34.dp),
                account = Account(
                    accountNo = account.accountNo,
                    bank = Bank(
                        bankId = account.bank.bankId,
                        bankCode = account.bank.bankCode,
                        bankName = account.bank.bankName
                    )
                ),
                fontColor = MainTextGray
            )
        }

    }
}

@Preview
@Composable
fun ConnectAccountPreview() {
    ConnectAccountScreen(onComplete = {})
}