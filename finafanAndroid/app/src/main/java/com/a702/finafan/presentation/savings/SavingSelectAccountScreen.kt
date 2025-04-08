package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 출금 계좌 선택 화면
@Composable
fun SavingSelectAccountScreen(
    savingViewModel: SavingViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {

    val navController = LocalNavController.current

    val context = LocalContext.current

    val accountState by accountViewModel.accountState.collectAsState()
    val savingState by savingViewModel.savingState.collectAsState()

    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

    val showAccountDialog = rememberSaveable { mutableStateOf(false) }

    // 출금계좌 목록 조회
    LaunchedEffect(Unit) {
        accountViewModel.fetchWithdrawalAccount()
    }

    LaunchedEffect(accountState.withdrawalAccounts) {
        if (!savingState.isLoading) {
            if (accountState.withdrawalAccounts.isEmpty()) {
                // 출금 계좌가 없으면 계좌 연결 페이지로 이동
                showAccountDialog.value = true
            } else {
                // 출금 계좌가 있으면 첫 번째 계좌를 연결
                val firstAccount = accountState.withdrawalAccounts.first()
                savingViewModel.updateSelectAccount(firstAccount)
            }
        }
    }

    if (showAccountDialog.value) {
        ConfirmDialog(
            showAccountDialog,
            content = context.getString(R.string.saving_item_withdrawal_empty),
            isConfirm = false,
            onClickConfirm = {
                showAccountDialog.value = false
                navController.navigate(NavRoutes.Account.route)
            }
        )
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = it.message ?: context.getString(R.string.saving_item_create_fail)

            savingViewModel.clearError()
        }
    }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                // 개설 완료 시
                if (savingState.createAccountId > 0) {
                    navController.navigate(NavRoutes.SavingMain.route + "/${savingState.createAccountId}") {
                        popUpTo(NavRoutes.SavingDesc.route) { inclusive = true }
                        launchSingleTop = true
                    }
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
        isButtonEnabled = savingState.selectAccount.accountNo.isNotEmpty(),
        onButtonClick = {
            // 적금 개설
            val savingCreate = SavingCreate(
                savingState.selectStar.starId,
                savingState.accountName,
                savingState.selectAccount
            )

            val request = savingCreate.toData()
            savingViewModel.createSaving(request)
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

        SelectAccountField(savingViewModel, accountState.withdrawalAccounts)

    }
}

@Preview
@Composable
fun SavingSelectAccountPreview() {
    SavingSelectAccountScreen()
}