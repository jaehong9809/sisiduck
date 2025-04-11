package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.component.Badge
import com.a702.finafan.common.ui.component.LiveTextField
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.starMidGreen

@Composable
fun SpendingListSection(
    items: List<Pair<String, String>>,
    onItemsChanged: (List<Pair<String, String>>) -> Unit,
    modifier: Modifier
) {
    var isAdding by remember { mutableStateOf(false) }
    var tempContent by remember { mutableStateOf("") }
    var tempAmount by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        SpendingHeader()
        items.forEachIndexed { index, (content, amount) ->
            SpendingItem(
                content = content,
                amount = amount,
                onDelete = {
                    val updated = items.toMutableList()
                    updated.removeAt(index)
                    onItemsChanged(updated)
                }
            )
        }

        if (isAdding) {
            SpendingItemInput(
                title = tempContent,
                amount = tempAmount,
                onTitleChange = { tempContent = it },
                onAmountChange = { tempAmount = it },
                onCancel = {
                    isAdding = false
                    tempContent = ""
                    tempAmount = ""
                },
                onComplete = {
                    if (tempContent.isNotBlank() && tempAmount.isNotBlank()) {
                        onItemsChanged(items + (tempContent to tempAmount))
                        isAdding = false
                        tempContent = ""
                        tempAmount = ""
                    }
                }
            )
        } else {
            AddSpendingButton(onClick = { isAdding = true })
        }

        // 합계
        val total = items.sumOf { it.second.replace(",", "").replace("원", "").toIntOrNull() ?: 0 }
        Text(
            text = "합계  ${"%,d".format(total)}원",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp, top = 20.dp, bottom = 20.dp),
            textAlign = TextAlign.End,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun SpendingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = "내용",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            text = "금액",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun SpendingItem(
    content: String,
    amount: String,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(
                text = content,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                lineHeight = 20.sp
            )
            Text(
                text = "${amount}원",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp
            )
        }

        Badge(
            content = "삭제",
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onDelete() },
            fontColor = MainWhite,
            bgColor = MainTextGray,
            widthMin = 60.dp,
            widthMax = 80.dp,
            height = 30.dp,
            fontSize = 12.sp
        )
    }
}


@Composable
fun SpendingItemInput(
    title: String,
    amount: String,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onComplete: () -> Unit,
    onCancel: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 10.dp)) {
        LiveTextField(
            hint = "내용 입력",
            value = title,
            onValueChange = onTitleChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        LiveTextField(
            hint = "금액 입력",
            value = amount,
            isMoney = true,
            onValueChange = onAmountChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Badge(
                content = "취소",
                modifier = Modifier.clickable{ onCancel() },
                fontColor = MainWhite,
                bgColor = starMidGreen,
                widthMin = 100.dp,
                widthMax = 120.dp,
                height = 45.dp,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Badge(
                content = "완료",
                modifier = Modifier.clickable{ onComplete() },
                fontColor = MainWhite,
                bgColor = starMidGreen,
                widthMin = 100.dp,
                widthMax = 120.dp,
                height = 45.dp,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun AddSpendingButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Badge(
            content = "항목 추가",
            modifier = Modifier.clickable{ onClick() }
                .padding(top = 10.dp),
            fontColor = MainWhite,
            bgColor = starMidGreen,
            widthMin = 100.dp,
            widthMax = 120.dp,
            height = 45.dp,
            fontSize = 14.sp
        )
    }

}
