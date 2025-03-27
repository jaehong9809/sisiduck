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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.utils.StringUtil

// 적금 해지 화면
@Composable
fun SavingCancelScreen(
    onComplete: () -> Unit
) {
    SavingScreenLayout(
        isFill = true,
        title = stringResource(R.string.saving_item_cancel_title),
        buttonText = stringResource(R.string.btn_account_cancel),
        isButtonEnabled = true,
        onButtonClick = {
            /* TODO: 적금 해지 절차 */
            onComplete()
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
                    text = StringUtil.formatCurrency(1203049),
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

            ProductDetailList()
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

            AccountInfoItem(account = Account("NH농협", "12-345-678900"), isCancel = true)
        }
    }
}

@Composable
fun ProductDetailList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductContentItem(stringResource(R.string.saving_item_origin_money), StringUtil.formatCurrency(1200000))
        ProductContentItem(stringResource(R.string.saving_item_duration), stringResource(R.string.format_duration, "2025.03.18", "2025.03.18"))
        ProductContentItem(stringResource(R.string.saving_item_interest), StringUtil.formatCurrency(1200000))
        ProductContentItem(stringResource(R.string.saving_item_total_money), StringUtil.formatCurrency(1200000))
    }
}

@Preview(showBackground = true)
@Composable
fun SavingCancelPreview() {
    SavingCancelScreen(onComplete = {})
}