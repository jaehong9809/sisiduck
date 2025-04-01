package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.account.BankEnum

// 계좌번호 정보 아이템 (은행 사진 + 은행명 + 계좌번호)
@Composable
fun AccountInfoItem(
    modifier: Modifier = Modifier,
    account: Account,
    isCancel: Boolean = false,
    fontColor: Color = MainBlack) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(if (isCancel) 34.dp else 44.dp),
            painter = painterResource(id = BankEnum.getDrawable(account.bank.bankCode)),
            contentDescription = "info",
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = account.bank.bankName + " " + account.accountNo,
            color = fontColor,
            fontSize = if (isCancel) 16.sp else 20.sp,
            fontWeight = if (isCancel) FontWeight.Normal else FontWeight.Medium,
            lineHeight = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountInfoItemPreview() {
    Column {
        AccountInfoItem(account = Account(
            accountId = 1234,
            accountNo = "456-789-1000",
            bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
        ))

        AccountInfoItem(account = Account(
            accountId = 1234,
            accountNo = "456-789-1000",
            bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
        ), isCancel = true)
    }
}