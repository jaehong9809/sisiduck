package com.a702.finafan.presentation.savings

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
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Star

// 적금 출금 계좌 선택 화면
@Composable
fun SavingSelectAccountScreen(
    star: Star, name: String?,
    onComplete: (Int) -> Unit
) {

    // TODO: 출금계좌 목록 조회 API 호출 -> 첫 번째 계좌 자동 선택

    val menuItems = mutableListOf("NH농협 312-0139-3754-31", "하나 312-0139-3754-31", "우리 312-0139-3754-31", "토스뱅크 312-0139-3754-31")
    val account = remember { mutableStateOf(menuItems[0]) }

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_select_account_title),
        buttonText = stringResource(R.string.btn_create),
        isButtonEnabled = account.value.isNotEmpty(),
        onButtonClick = {
            /* TODO: 적금 개설 API 호출, 개설 완료 후에 적금계좌 고유번호 넘기기 */
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

        SelectAccountField(menuItems)

    }
}

@Preview
@Composable
fun SavingSelectAccountPreview() {
    SavingSelectAccountScreen(Star(entertainerName = "이찬원"), "적금이름", onComplete = {})
}