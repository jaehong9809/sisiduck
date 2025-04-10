package com.a702.finafan.common.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import com.a702.finafan.domain.savings.model.Star

// 스타 추가 다이얼로그
@Composable
fun AddStarDialog(
    showDialog: MutableState<Boolean>,
    star: Star,
    onClickConfirm: () -> Unit
) {
    DialogLayout(
        showDialog = showDialog,
        isConfirm = false,
        confirmBtnText = stringResource(R.string.btn_add),
        onClickConfirm = onClickConfirm
    ) {
        CircleBorderImage(
            modifier = Modifier.size(60.dp),
            imageUrl = star.starImageUrl
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = star.starName,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = MainBlack,
            fontSize = 22.sp
        )
    }
}

@Preview
@Composable
fun AddStarDialogPreview() {
    AddStarDialog (showDialog = remember { mutableStateOf(false) },
        Star(starName = "이찬원"), onClickConfirm = {  })
}