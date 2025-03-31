package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.data.savings.dto.request.toData
import com.a702.finafan.domain.savings.model.SavingCreate
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 출금 계좌 선택 화면
@Composable
fun SavingSelectAccountScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: (Long) -> Unit
) {

    val context = LocalContext.current
    val savingState by viewModel.savingState.collectAsState()

    val showDialog = remember { mutableStateOf(false) }
    val dialogContent = remember { mutableStateOf("") }

    // 출금계좌 목록 조회
    LaunchedEffect(Unit) {
        viewModel.fetchWithdrawalAccount()
    }

    // 첫 번째 계좌 자동 선택
    LaunchedEffect(savingState.withdrawalAccounts) {
        if (savingState.withdrawalAccounts.isNotEmpty()) {
            val firstAccount = savingState.withdrawalAccounts.first()
            viewModel.updateSavingConnectAccount(firstAccount)
        } else {

        }
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = it.message ?: context.getString(R.string.saving_item_create_fail)
        }
    }

    if (showDialog.value) {
        ConfirmDialog(
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                // 개설 완료 시
                if (savingState.createAccountId > 0) {
                    onComplete(savingState.createAccountId)
                }
            }
        )
    }

    LaunchedEffect(savingState.createAccountId) {
        if (savingState.createAccountId > 0) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_start, savingState.accountName)
        }
    }

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_select_account_title),
        buttonText = stringResource(R.string.btn_create),
        isButtonEnabled = savingState.connectAccount.accountNo.isNotEmpty(),
        onButtonClick = {
            // 적금 개설
            val savingCreate = SavingCreate(
                savingState.selectStar.entertainerId,
                savingState.accountName,
                savingState.connectAccount
            )

            val request = savingCreate.toData()
            viewModel.createSaving(request)
        }
    ) {

        Text(
            modifier = Modifier.padding(top = 22.dp, bottom = 46.dp),
            text = stringResource(R.string.saving_item_select_account_desc),
            color = MainTextGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start
        )

        SelectAccountField(viewModel, savingState.withdrawalAccounts)

    }
}

@Preview
@Composable
fun SavingSelectAccountPreview() {
    SavingSelectAccountScreen(onComplete = {})
}