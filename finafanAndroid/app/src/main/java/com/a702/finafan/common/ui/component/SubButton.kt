package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainWhite

// 테두리 버튼
@Composable
fun SubButton(text: String, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onButtonClick() }
            .background(color = MainWhite, shape = RoundedCornerShape(10.dp))
            .border(1.dp, EditTextGray, shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 14.dp),
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = EditTextGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SubButtonPreview() {
    SubButton(stringResource(R.string.saving_item_change_name_label), onButtonClick = { })
}