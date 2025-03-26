package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CommonBottomSheet(
    title: String? = null,
    content: String,
    isInfo: Boolean = true,
    confirmBtnText: String = stringResource(R.string.btn_confirm),
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        isBottom = true,
        isInfo = isInfo,
        confirmBtnText = confirmBtnText,
        onClickConfirm = onClickConfirm
    ) {
        title?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MainBlack,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = content,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MainBlack,
            fontSize = 20.sp
        )
    }
}

// TODO: 적금 이름 변경 바텀시트 추가

@Preview
@Composable
fun CommonBottomPreview() {
//    CommonBottomSheet(
//        title = "제목",
//        content = "내용",
//        isInfo = false,
//        onClickConfirm = {  }
//    )

    CommonBottomSheet(
        content = "내용",
        onClickConfirm = {  }
    )
}