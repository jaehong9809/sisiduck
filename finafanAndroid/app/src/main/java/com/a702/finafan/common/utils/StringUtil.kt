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
}