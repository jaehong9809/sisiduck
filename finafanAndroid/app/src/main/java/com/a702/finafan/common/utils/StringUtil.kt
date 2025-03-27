package com.a702.finafan.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.a702.finafan.R
import java.text.NumberFormat
import java.util.Locale

object StringUtil {
    @Composable
    fun formatCurrency(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedAmount = formatter.format(amount)

        return stringResource(R.string.format_money, formattedAmount)
    }

    fun formatEditMoney(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        return formatter.format(amount)
    }
}