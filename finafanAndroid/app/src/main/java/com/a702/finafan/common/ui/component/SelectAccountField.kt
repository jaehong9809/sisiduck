package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite

// 출금 계좌 선택 박스
@Composable
fun SelectAccountField(menuItems: MutableList<String>) {
    var expandStatus by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(menuItems[0]) }

    TextItem(stringResource(R.string.withdraw_account_label), MainBlack, 16.sp, true)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
            .clickable {
                expandStatus = true
            }
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextItem(selectedAccount, MainBlack, 20.sp)
            Spacer(modifier = Modifier.width(width = 8.dp))
            Icon(
                painter = painterResource(R.drawable.angle_down),
                contentDescription = "",
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(MainWhite)
                .wrapContentSize(),
            expanded = expandStatus,
            onDismissRequest = {
                expandStatus = false
            }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item, fontSize = 20.sp, color = MainBlack, fontWeight = FontWeight.Normal) },
                    onClick = {
                        selectedAccount = item
                        expandStatus = false
                        println("Selected: $item")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAccountPreview() {
    val menuItems = mutableListOf("NH농협 312-0139-3754-31", "하나 312-0139-3754-31", "우리 312-0139-3754-31", "토스뱅크 312-0139-3754-31")
    SelectAccountField(menuItems = menuItems)
}