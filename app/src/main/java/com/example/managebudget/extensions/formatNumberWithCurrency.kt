package com.example.managebudget.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

fun formatNumberWithCurrency(number: Int): String {
    val persianLocale = Locale("fa", "IR")
    val symbols = DecimalFormatSymbols(persianLocale)

    val decimalFormat = DecimalFormat("#,###", symbols)
    return decimalFormat.format(number)
}
