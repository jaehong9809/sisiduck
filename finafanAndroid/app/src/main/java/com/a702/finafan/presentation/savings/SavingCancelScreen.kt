package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.presentation.account.AccountInfoItem
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 해지 화면
@Composable
fun SavingCancelScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val context = LocalContext.current

    val savingState by viewModel.savingState.collectAsState()
    val accountInfo = savingState.savingInfo.savingAccount
    val withdrawalAccount = accountInfo.withdrawalAccount

    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                if (savingState.isCancel) {
                    viewModel.resetCancelState() // 상태 초기화
                    onComplete()
                }
            }
        )
    }

    LaunchedEffect(savingState.isCancel) {
        if (savingState.isCancel) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_saving_cancel_complete)
        }
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = savingState.error?.message.toString()

            viewModel.clearError()
        }
    }

    SavingScreenLayout(
        isFill = true,
        title = stringResource(R.string.saving_item_cancel_title),
        buttonText = stringResource(R.string.btn_account_cancel),
        isButtonEnabled = true,
        onButtonClick = {
            // 적금 해지
            viewModel.deleteSavingAccount(accountInfo.accountId)
        }
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.saving_item_total_origin_interest),
                    color = MainTextGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                )

                Text(
                    text = StringUtil.formatCurrency(accountInfo.amount),
                    color = MainBlack,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MainBgLightGray)
                )

            }

            ProductDetailList(accountInfo)
        }

        Box(
            modifier = Modifier
                .padding(vertical = 34.dp)
                .fillMaxWidth()
                .height(18.dp)
                .background(MainBgLightGray)
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.saving_item_deposit_account),
                color = MainBlack ,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp,
            )

            AccountInfoItem(
                account = withdrawalAccount,
                isCancel = true
            )
        }
    }
}

@Composable
fun ProductDetailList(accountInfo: SavingAccount) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductContentItem(
            stringResource(R.string.saving_item_origin_money),
            StringUtil.formatCurrency(accountInfo.amount)
        )
        ProductContentItem(
            stringResource(R.string.saving_item_duration), stringResource(
                R.string.format_duration,
                StringUtil.formatDate(accountInfo.createdDate), StringUtil.getTodayFormattedDate()
            )
        )
        ProductContentItem(
            stringResource(R.string.saving_item_interest),
            StringUtil.formatCurrency(0)
        )
        ProductContentItem(
            stringResource(R.string.saving_item_total_money),
            StringUtil.formatCurrency(accountInfo.amount)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SavingCancelPreview() {
    SavingCancelScreen(onComplete = {})
}