package com.a702.finafan.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.gradientBlue
import com.a702.finafan.domain.savings.model.Bank

@Composable
fun BankItem(
    bank: Bank,
    isSelected: Boolean,
    onSelect: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(MainBgLightGray, shape = RoundedCornerShape(15.dp))
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, brush = gradientBlue, shape = RoundedCornerShape(15.dp))
                } else {
                    Modifier
                }
            )
            .clickable {
                onSelect(bank.bankId)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(68.dp),
                painter = painterResource(id = BankEnum.getDrawable(bank.bankCode)),
                contentDescription = "bankImage",
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = bank.bankName,
                color = MainBlack ,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BankItemPreview() {
    var selectedBank = "NH농협"

    BankItem(Bank(bankId = 12, bankCode = "011", bankName = "NH농협"), false, onSelect = {})
}