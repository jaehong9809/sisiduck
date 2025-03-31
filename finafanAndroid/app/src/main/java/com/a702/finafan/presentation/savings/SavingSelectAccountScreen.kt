package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.data.savings.dto.request.toData
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.SavingCreate
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 적금 출금 계좌 선택 화면
@Composable
fun SavingSelectAccountScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: (Int) -> Unit
) {

    val savingState by viewModel.savingState.collectAsState()

    // TODO: 출금계좌 목록 조회 API 호출 -> 첫 번째 계좌 자동 선택
    val accounts = mutableListOf(
        Account(1, "123-456", Bank(1, "123", "NH농협")),
        Account(2, "456-789", Bank(2, "123", "토스뱅크"))
    )

//    viewModel.updateSavingConnectAccount(accounts[1])

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_select_account_title),
        buttonText = stringResource(R.string.btn_create),
        isButtonEnabled = savingState.connectAccount.accountNo.isNotEmpty(),
        onButtonClick = {
            /* TODO: 적금 개설 API 호출, 개설 완료 후에 적금계좌 고유번호 넘기기 */
            val savingCreate = SavingCreate(
                savingState.selectStar.entertainerId,
                savingState.accountName,
                savingState.connectAccount
            )

            val request = savingCreate.toData()

            onComplete(1)
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

        SelectAccountField(viewModel, accounts)

    }
}

@Preview
@Composable
fun SavingSelectAccountPreview() {
    SavingSelectAccountScreen(onComplete = {})
}