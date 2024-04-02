package com.example.managebudget.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun formatTomanWithCommas(amount: Long): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
    val billion = amount / 1_000_000_000
    val million = (amount % 1_000_000_000) / 1_000_000
    val thousand = (amount % 1_000_000) / 1_000
    val remainder = amount % 1_000

    val decimalForMillion = if (remainder > 0 && million > 0) {
        formatter.format(million + remainder / 1_000_000.0)
    } else if (million > 0) {
        formatter.format(million)
    } else {
        ""
    }

    val decimalForBillion = if (million > 0 || remainder > 0) {
        formatter.format(billion + (million + remainder / 1_000_000.0) / 1_000.0)
    } else {
        formatter.format(billion)
    }

    return when {
        billion > 0 -> "$decimalForBillion  میلیارد تومان"
        million > 0 -> "$decimalForMillion  میلیون تومان"
        thousand > 0 -> formatter.format(thousand) + "  هزار تومان"
        else -> formatter.format(amount) + "  تومان"
    }
}