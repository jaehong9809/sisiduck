package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.a702.finafan.R

@Composable
fun DialogButton(
    isInfo: Boolean = true,
    confirmBtnText: String,
    onClickConfirm: () -> Unit,
    isDialogVisible: MutableState<Boolean>,
    btnEnabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 확인용은 취소 버튼 없애기
        if (!isInfo) {
            CommonCancelButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { isDialogVisible.value = false },
                text = stringResource(R.string.btn_cancel)
            )
        }

        PrimaryGradButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onClick = {
                onClickConfirm()
                isDialogVisible.value = false
            },
            text = confirmBtnText,
            isEnabled = btnEnabled
        )
    }
}