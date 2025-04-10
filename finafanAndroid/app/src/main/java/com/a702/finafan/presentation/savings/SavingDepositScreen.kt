package com.a702.finafan.presentation.savings

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.ImageField
import com.a702.finafan.common.ui.component.NumberField
import com.a702.finafan.common.ui.component.StringField
import com.a702.finafan.common.utils.MediaUtil.asMultipart
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.data.savings.dto.request.toData
import com.a702.finafan.domain.savings.model.SavingDeposit
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun SavingDepositScreen(
    viewModel: SavingViewModel = viewModel(),
    savingAccountId: Long,
) {

    val navController = LocalNavController.current

    val context = LocalContext.current
    val savingState by viewModel.savingState.collectAsState()

    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

    val message = rememberSaveable { mutableStateOf("") }
    val money = rememberSaveable { mutableStateOf("") }
    val image = remember { mutableStateOf(Uri.EMPTY) }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false

                // 입금 완료 시 완료 후에 적금 내역으로 이동
                if (savingState.depositAccountId > 0) {
                    viewModel.resetDeposit()

                    navController.navigate(NavRoutes.SavingMain.route + "/${savingAccountId}") {
                        popUpTo(NavRoutes.SavingDeposit.route + "/${savingAccountId}") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        )
    }

    LaunchedEffect(savingState.depositAccountId) {
        if (savingState.depositAccountId > 0) {
            showDialog.value = true
            dialogContent.value = context.getString(R.string.saving_item_deposit_complete)
        }
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = it.message ?: context.getString(R.string.withdraw_server_fail)

            viewModel.clearError()
        }
    }

    SavingScreenLayout(
        title = stringResource(R.string.saving_item_deposit_title),
        buttonText = stringResource(R.string.btn_deposit),
        isButtonEnabled = message.value.isNotEmpty() && money.value.isNotEmpty(),
        onButtonClick = {

            val contentResolver: ContentResolver = context.contentResolver
            val imageMultipart = image.value.asMultipart("file", contentResolver)

            val savingDeposit = SavingDeposit(
                savingAccountId = savingAccountId,
                message = message.value,
                amount = StringUtil.formatNumber(money.value),
                imageFile = imageMultipart
            )

            val request = savingDeposit.toData()
            viewModel.depositSaving(request)
        }
    ) {

        // 메시지 필드
        StringField(
            modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
            label = stringResource(R.string.saving_message_label),
            hint = stringResource(R.string.saving_message_hint),
            text = message,
            maxLength = 20
        )

        // 입금 금액
        NumberField(
            modifier = Modifier.padding(top = 34.dp, bottom = 12.dp),
            label = stringResource(R.string.money_label),
            hint = stringResource(R.string.money_hint),
            text = money,
            isMoney = true
        )

        // 사진 필드
        ImageField(
            modifier = Modifier.padding(top = 34.dp),
            label = stringResource(R.string.saving_item_deposit_image_label),
            selectImage = image
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun SavingDepositPreview() {
    SavingDepositScreen(savingAccountId = 0)
}