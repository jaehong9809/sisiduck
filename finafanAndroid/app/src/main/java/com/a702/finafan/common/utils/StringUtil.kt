package com.a702.finafan.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.a702.finafan.R
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object StringUtil {
    @Composable
    fun formatCurrency(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedAmount = formatter.format(amount)

        return stringResource(R.string.format_money, formattedAmount)
    }

    fun formatNumber(amount: String): Long {
        val digitsOnly = amount.replace(Regex("[^0-9]"), "")
        return if (digitsOnly.isBlank()) 0L else digitsOnly.toLong()
    }

    fun formatEditMoney(amount: String): String {
        val num = formatNumber(amount)
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)

        return formatter.format(num)
    }

    fun formatPercentage(currentAmount: Long, goalAmount: Long): Int {
        return if (goalAmount > 0) {
            (currentAmount.toFloat() / goalAmount.toFloat() * 100).toInt()
        } else {
            0
        }
    }

    fun formatDate(dateString: String): String {
        if (dateString.isEmpty()) {
            return ""
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val dateTime = LocalDateTime.parse(dateString, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return dateTime.format(outputFormatter)
    }

    fun getTodayFormattedDate(): String {
        val now = LocalDateTime.now()

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return now.format(outputFormatter)
    }
}