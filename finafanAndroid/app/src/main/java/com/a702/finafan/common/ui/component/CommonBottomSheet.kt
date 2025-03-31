package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

// 공통 바텀시트
@Composable
fun ConfirmBottomSheet(
    title: String? = null,
    content: String,
    isInfo: Boolean = true,
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        isBottom = true,
        isConfirm = isInfo,
        onClickConfirm = onClickConfirm
    ) {
        title?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MainBlack,
                fontSize = 24.sp,
                lineHeight = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = content,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MainBlack,
            fontSize = 20.sp,
            lineHeight = 30.sp
        )
    }
}

// 적금 이름 변경 바텀시트
@Composable
fun SavingNameBottomSheet(
    name: MutableState<String>,
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        isBottom = true,
        confirmBtnText = stringResource(R.string.btn_change),
        onClickConfirm = onClickConfirm,
        btnEnabled = name.value.isNotEmpty()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            text = stringResource(R.string.saving_item_change_name),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MainBlack,
            fontSize = 24.sp,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        StringField(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            label = stringResource(R.string.username_label),
            hint = stringResource(R.string.name_hint),
            text = name,
            isSaving = true
        )
    }
}

@Preview
@Composable
fun CommonBottomPreview() {
    ConfirmBottomSheet(
        content = "내용",
        onClickConfirm = {  }
    )
}

@Preview
@Composable
fun CommonBottomWithTitlePreview() {
    ConfirmBottomSheet(
        title = "제목",
        content = "내용",
        onClickConfirm = {  }
    )
}

@Preview
@Composable
fun SavingNameBottomPreview() {
    SavingNameBottomSheet(
        remember { mutableStateOf("이찬원") }
    ) {
        // 이름 변경
    }
}