package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.NumberField
import com.a702.finafan.common.ui.component.SubButton
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 인증 코드 입력 화면
@Composable
fun AccountCodeScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit,
    onNavigateClick: () -> Unit
) {

    val savingState by viewModel.savingState.collectAsState()
    val selectBank = savingState.selectBank
    val code = remember { mutableStateOf("") }

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_verification_code_title, selectBank.bankName),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = code.value.isNotEmpty(),
        onButtonClick = {
            onComplete()
        }
    ) {

        Column {
            AccountInfoItem(
                modifier = Modifier.padding(top = 34.dp),
                account = Account(
                    accountNo = savingState.inputAccountNo,
                    bank = Bank(
                        bankId = selectBank.bankId,
                        bankCode = selectBank.bankCode,
                        bankName = selectBank.bankName
                    )
                ),
                fontColor = MainTextGray
            )

            // 인증번호 입력 필드
            NumberField(
                modifier = Modifier.padding(top = 34.dp),
                label = stringResource(R.string.account_code_label),
                hint = stringResource(R.string.account_code_hint),
                text = code)

            Spacer(modifier = Modifier.height(18.dp))

            SubButton(
                modifier = Modifier.padding(start = 4.dp),
                text = "다른 계좌로 인증하기",
                onButtonClick = {
                    onNavigateClick()
                })
        }

    }
}

@Preview
@Composable
fun AccountCodePreview() {
    AccountCodeScreen(onComplete = {}, onNavigateClick = {})
}