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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.EditBgGray
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.presentation.funding.component.MenuDescription
import com.a702.finafan.presentation.funding.component.MenuTitle
import java.time.LocalDate
import java.util.Calendar

@Composable
fun DatePickerView(
    label: String,
    desc: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selected = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selected)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuTitle(label)
            Button(
                onClick = { datePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(containerColor = MainTextBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(45.dp)
            ) {
                Text(text = stringResource(R.string.funding_create_goal_date_btn), color = MainWhite, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
        MenuDescription(desc)
        Spacer(modifier = Modifier.height(12.dp))

        DateDisplay(selectedDate = selectedDate)
    }
}

@Composable
fun DateDisplay(
    selectedDate: LocalDate?,
) {
    val year = selectedDate?.year?.toString() ?: ""
    val month = selectedDate?.monthValue?.toString()?.padStart(2, '0') ?: ""
    val day = selectedDate?.dayOfMonth?.toString()?.padStart(2, '0') ?: ""

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DisplayOnlyTextField(value = year, hint = "", width = 90.dp)
        Text(text = stringResource(R.string.funding_create_goal_date_year), fontSize = 20.sp)

        DisplayOnlyTextField(value = month, hint = "", width = 60.dp)
        Text(text = stringResource(R.string.funding_create_goal_date_month), fontSize = 20.sp)

        DisplayOnlyTextField(value = day, hint = "", width = 60.dp)
        Text(text = stringResource(R.string.funding_create_goal_date_day), fontSize = 20.sp)
    }
}


@Composable
fun DisplayOnlyTextField(
    value: String,
    hint: String,
    width: Dp = 100.dp
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


