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
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack

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
        // TODO: 은행명에 해당하는 은행 사진
        Image(
            modifier = Modifier
                .size(if (isCancel) 34.dp else 44.dp),
            painter = painterResource(id = R.drawable.info_circle),
            contentDescription = "info",
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = account.bankName + " " + account.accountNum,
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
        AccountInfoItem(account = Account("NH농협", "12-345-678900"))
        AccountInfoItem(account = Account("NH농협", "12-345-678900"), isCancel = true)
    }
}