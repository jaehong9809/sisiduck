package com.a702.finafan.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun ConnectAccountLayout(
    topBarTitle: String? = null,
    title: String,
    buttonText: String,
    isButtonEnabled: Boolean,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 공통 상단 바
        CommonBackTopBar(modifier = Modifier, text = topBarTitle, isTextCentered = true)

        LazyColumn (
            modifier = Modifier
                .weight(1f)
//                .verticalScroll(rememberScrollState())
                .background(MainWhite)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(top = 36.dp),
                    text = title,
                    color = MainBlack,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Start
                )
            }

            item { content() }
        }

        // 공통 하단 버튼
        PrimaryGradBottomButton(
            modifier = Modifier.fillMaxWidth().imePadding(),
            onClick = onButtonClick,
            text = buttonText,
            isEnabled = isButtonEnabled
        )
    }
}
