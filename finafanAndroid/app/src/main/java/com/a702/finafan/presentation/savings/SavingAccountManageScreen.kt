package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ConfirmDialog
import com.a702.finafan.common.ui.component.SavingNameBottomSheet
import com.a702.finafan.common.ui.component.SubButton
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun SavingAccountManageScreen(
    viewModel: SavingViewModel = viewModel(),
    onCancelClick: () -> Unit
) {

    val savingState by viewModel.savingState.collectAsState()
    val accountInfo = savingState.savingAccount

    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val dialogContent = rememberSaveable { mutableStateOf("") }

    val changeName = rememberSaveable { mutableStateOf(accountInfo.accountName) }
    val originName = rememberSaveable { mutableStateOf(accountInfo.accountName) }

    LaunchedEffect(accountInfo) {
        showBottomSheet.value = false

        if (accountInfo.accountName.isNotEmpty()) {
            originName.value = accountInfo.accountName
        }
    }

    if (showBottomSheet.value) {
        SavingNameBottomSheet(changeName, showBottomSheet) {
            viewModel.changeSavingName(accountInfo.accountId, changeName.value)
        }
    }

    if (showDialog.value) {
        ConfirmDialog(
            showDialog,
            content = dialogContent.value,
            onClickConfirm = {
                showDialog.value = false
            }
        )
    }

    LaunchedEffect(savingState.error) {
        savingState.error?.let {
            showDialog.value = true
            dialogContent.value = savingState.error?.message.toString()

            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                text = stringResource(R.string.saving_account_manage_title),
                isTextCentered = true
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .background(MainWhite)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(accountInfo.imageUrl)
                            .build()
                    )

                    Image(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(LightGray),
                        painter = painter,
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier.padding(top = 54.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.saving_item_name, originName.value),
                                color = MainBlack,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 24.sp,
                            )

                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = accountInfo.accountNo,
                                color = MainTextGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 24.sp,
                            )
                        }

                        SubButton(
                            text = stringResource(R.string.saving_item_change_name_label),
                            onButtonClick = {
                                showBottomSheet.value = true
                            }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MainBgLightGray)
                    )

                    AccountDetailList(accountInfo)
                }

                Box(
                    modifier = Modifier
                        .padding(top = 34.dp)
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(MainBgLightGray)
                )

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onCancelClick() }
                        ),
                    text = stringResource(R.string.btn_account_cancel),
                    color = MainTextGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                )
            }

        }
    }

}

@Composable
fun AccountDetailList(accountInfo: SavingAccount) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductContentItem(stringResource(R.string.saving_item_product_name), stringResource(R.string.saving_item_product_name_title))
        ProductContentItem(stringResource(R.string.saving_item_start_date), StringUtil.formatDate(accountInfo.createdDate))
        ProductContentItem(stringResource(R.string.saving_item_connect_account), accountInfo.withdrawalAccount.bank.bankName + " " + accountInfo.withdrawalAccount.accountNo)
        ProductContentItem(stringResource(R.string.saving_item_apply_interest), accountInfo.interestRate)
    }
}

@Preview
@Composable
fun SavingAccountInfoPreview() {
    SavingAccountManageScreen(onCancelClick = {})
}