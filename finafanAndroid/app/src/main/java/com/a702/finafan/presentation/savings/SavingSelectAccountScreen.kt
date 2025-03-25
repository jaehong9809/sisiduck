package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.SelectAccountField
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun SavingSelectAccountScreen() {
    val account = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 상단 바
        CommonBackTopBar(modifier = Modifier, imageOnClick = {},
            text = stringResource(R.string.saving_item_create_top_bar), isTextCentered = true)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .background(MainWhite)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.saving_item_select_account_title),
                color = MainBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp,
                modifier = Modifier.padding(top = 36.dp),
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier.padding(top = 22.dp, bottom = 46.dp),
                text = stringResource(R.string.saving_item_select_account_desc),
                color = MainTextGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
                textAlign = TextAlign.Start
            )

            val menuItems = mutableListOf("NH농협 312-0139-3754-31", "하나 312-0139-3754-31", "우리 312-0139-3754-31", "토스뱅크 312-0139-3754-31")
            SelectAccountField(menuItems)
        }

        // 하단 버튼
        PrimaryGradBottomButton(
            modifier = Modifier.fillMaxWidth()
                .imePadding(),
            onClick = {},
            text = stringResource(R.string.btn_create),
            isEnabled = account.value.isNotEmpty()
        )
    }
}

@Preview
@Composable
fun SavingSelectAccountPreview() {
    SavingSelectAccountScreen()
}