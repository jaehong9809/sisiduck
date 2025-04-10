package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun DialogLayout(
    showDialog: MutableState<Boolean>,
    isConfirm: Boolean = true,
    confirmBtnText: String = stringResource(R.string.btn_confirm),
    onClickConfirm: () -> Unit,
    btnEnabled: Boolean = true,
    content: @Composable () -> Unit
) {

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MainWhite, shape = RoundedCornerShape(25.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    content()

                    Spacer(modifier = Modifier.height(28.dp))

                    DialogButton(
                        isConfirm,
                        confirmBtnText,
                        onClickConfirm,
                        showDialog,
                        btnEnabled
                    )
                }
            }
        }
    }
}