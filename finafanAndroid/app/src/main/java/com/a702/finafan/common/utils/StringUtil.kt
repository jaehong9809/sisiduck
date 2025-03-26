package com.a702.finafan.common.utils

import java.text.NumberFormat
import java.util.Locale

object StringUtil {
    fun formatCurrency(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        return formatter.format(amount) + "원"
    }

    fun formatEditMoney(amount: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
        return formatter.format(amount)
    }

    fun formatPercentage(currentAmount: Long, goalAmount: Long): Int {
        return if (goalAmount > 0) {
            (currentAmount.toFloat() / goalAmount.toFloat() * 100).toInt()
        } else {
            0
        }
    }
}