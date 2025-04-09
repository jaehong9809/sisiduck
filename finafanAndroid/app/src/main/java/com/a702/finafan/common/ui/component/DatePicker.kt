package com.a702.finafan.common.ui.component

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBlack
import java.time.LocalDate
import java.util.Calendar

@Composable
fun DatePicker(onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val date = remember { mutableStateOf<LocalDate?>(null) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selected = LocalDate.of(year, month + 1, dayOfMonth)
            date.value = selected
            onDateSelected(selected)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val selectedDate = date.value
    val year = selectedDate?.year?.toString() ?: ""
    val month = selectedDate?.monthValue?.toString()?.padStart(2, '0') ?: ""
    val day = selectedDate?.dayOfMonth?.toString()?.padStart(2, '0') ?: ""

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DisplayOnlyTextField(value = year, hint = "", width = 80.dp)
            Text("년", fontSize = 20.sp)

            DisplayOnlyTextField(value = month, hint = "", width = 60.dp)
            Text("월", fontSize = 20.sp)

            DisplayOnlyTextField(value = day, hint = "", width = 60.dp)
            Text("일", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { datePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(containerColor = EditBgGray),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Text("날짜 선택", color = MainBlack)
        }
    }
}

@Composable
fun DisplayOnlyTextField(
    value: String,
    hint: String,
    width: Dp = 100.dp // 기본값 넣어둠
) {
    BasicTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        readOnly = true,
        textStyle = TextStyle(color = MainBlack, fontSize = 20.sp, textAlign = TextAlign.Center),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .width(width)
                    .background(color = EditBgGray, shape = RoundedCornerShape(18.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (value.isEmpty()) {
                    TextItem(hint, EditTextGray, 20.sp)
                }
                innerTextField()
            }
        }
    )
}


