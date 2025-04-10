package com.a702.finafan.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.a702.finafan.R
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
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

    @Composable
    fun getRemainingAmountText(currentAmount: Long, goalAmount: Long): String {
        val remaining = (goalAmount - currentAmount).coerceAtLeast(0L)
        val formatted = "%,d".format(remaining)
        return stringResource(id = R.string.funding_remaining_amount, formatted)
    }

    fun formatPercentage(currentAmount: Long, goalAmount: Long): Int {
        return if (goalAmount > 0) {
            (currentAmount.toFloat() / goalAmount.toFloat() * 100).toInt()
        } else {
            0
        }
    }

    @Composable
    fun getFundingProgressText(currentAmount: Long, goalAmount: Long): String {
        val percentage = if (goalAmount > 0) {
            (currentAmount.toFloat() / goalAmount.toFloat() * 100).toInt()
        } else {
            0
        }
        return stringResource(id = R.string.funding_progress_percentage, percentage)
    }

    fun formatDate(dateString: String): String {
        if (dateString.isEmpty()) {
            return ""
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return dateTime.format(outputFormatter)
    }

    fun formatDetailDate(dateString: String): String {
        if (dateString.isEmpty()) {
            return ""
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH:mm")
        return dateTime.format(outputFormatter)
    }

    fun formatTime(dateString: String): String {
        if (dateString.isEmpty()) {
            return ""
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateString, formatter)

        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return dateTime.format(outputFormatter)
    }

    fun getTodayFormattedDate(): String {
        val now = LocalDateTime.now()

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return now.format(outputFormatter)
    }

    @Composable
    fun getRemainingDaysText(targetDate: LocalDate): String {
        val today = LocalDate.now()
        val daysLeft = ChronoUnit.DAYS.between(today, targetDate)

        return when {
            daysLeft > 0 -> stringResource(R.string.funding_remaining_days, daysLeft)
            daysLeft == 0L -> stringResource(R.string.funding_ends_today)
            else -> stringResource(R.string.funding_already_ended)
        }
    }
}