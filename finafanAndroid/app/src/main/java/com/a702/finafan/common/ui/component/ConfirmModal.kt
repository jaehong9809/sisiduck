package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack

// 공통 다이얼로그
@Composable
fun ConfirmDialog(
    showDialog: MutableState<Boolean>,
    title: String? = null,
    content: String,
    isConfirm: Boolean = true,
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        showDialog = showDialog,
        isConfirm = isConfirm,
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

@Preview
@Composable
fun ConfirmDialogPreview() {
    ConfirmDialog(
        showDialog = remember { mutableStateOf(false) },
        content = "내용",
        onClickConfirm = {  }
    )
}

@Preview
@Composable
fun ConfirmWithTitlePreview() {
    ConfirmDialog(
        showDialog = remember { mutableStateOf(false) },
        title = "제목",
        content = "내용",
        isConfirm = false,
        onClickConfirm = {  }
    )
}
