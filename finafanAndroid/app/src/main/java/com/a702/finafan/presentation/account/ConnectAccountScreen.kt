package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel

// 연결 계좌 확인 화면
@Composable
fun ConnectAccountScreen(
    viewModel: AccountViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val context = LocalContext.current

    val accountState by viewModel.accountState.collectAsState()
    val account = accountState.connectAccount

    val showDeleteDialog = rememberSaveable { mutableStateOf(false) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

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

                if (accountState.isCancel) {
                    viewModel.resetCancelState() // 상태 초기화
                    onComplete()
                }
            }
        )
    }

    LaunchedEffect(accountState.isCancel) {
        if (accountState.isCancel) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_connect_cancel_complete)
        }
    }

    LaunchedEffect(accountState.error) {
        accountState.error?.let {
            showDialog.value = true
            dialogContent.value = accountState.error?.message.toString()

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
                account = account,
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