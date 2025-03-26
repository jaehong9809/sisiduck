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
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.gradientBlue

@Composable
fun BankItem(bankName: String, isSelected: Boolean, onSelect: (String) -> Unit) {
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
                onSelect(bankName)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 42.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: 은행 이미지 추가
            Image(
                modifier = Modifier
                    .size(68.dp),
                painter = painterResource(id = R.drawable.info_circle),
                contentDescription = "bankImage",
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = bankName,
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

    BankItem(bankName = "NH농협", isSelected = selectedBank == "NH농협", onSelect = { selected ->
        selectedBank = selected
        println("Selected bank: $selected")
    })
}