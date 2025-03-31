package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.domain.savings.model.Bank

// 은행 선택 화면
@Composable
fun ConnectBankScreen(
    onSelect: (bank: Bank) -> Unit
) {

    val selectBank = remember { mutableStateOf(Bank(111, "111", "NH농협")) }

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_select_back_title),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = selectBank.value.bankName.isNotEmpty(),
        onButtonClick = { onSelect(selectBank.value) }
    ) {

        // TODO: 은행 목록 API 호출

        Column {
            // 은행 목록
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 34.dp),
                text = stringResource(R.string.select_bank_label),
                color = MainBlack,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
                textAlign = TextAlign.Start
            )

            // TODO: 은행 목록 스크롤 문제 수정 필요
//            LazyVerticalGrid (
//                columns = GridCells.Fixed(2),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp)
//                    .wrapContentHeight(),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                userScrollEnabled = false
//            ) {
//                items(bankList) { bank ->
//                    val bankName = bank
//                    val isSelected = selectBank.value == bankName
//
//                    BankItem(
//                        bankName = bankName,
//                        isSelected = isSelected,
//                        onSelect = {
//                            selectBank.value = it
//                        }
//                    )
//                }
//            }

        }

    }
}

@Preview
@Composable
fun ConnectBankPreview() {
    val bankList = mutableListOf("NH농협", "우리은행", "하나은행", "국민은행", "신한은행", "카카오뱅크", "토스", "기업은행")
    ConnectBankScreen(onSelect = {})
}