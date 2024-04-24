package com.example.managebudget.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoField

@RequiresApi(Build.VERSION_CODES.O)
fun convertGregorianToPersian(gregorianDate: LocalDate): String {
        val gYear = gregorianDate.get(ChronoField.YEAR)
        val gMonth = gregorianDate.get(ChronoField.MONTH_OF_YEAR)
        val gDay = gregorianDate.get(ChronoField.DAY_OF_MONTH)

        // Approximate conversion of Gregorian to Persian (this is a simplistic example and needs refinement for actual use)
        var pYear = gYear - 621
        var pMonth = gMonth - 3
        var pDay = gDay - 10

        // Adjust day and month ranges
        if (pMonth <= 0) {
            pMonth += 12
            pYear -= 1
        }

        if (pDay <= 0) {
            pDay += 30 // This is very approximate; actual calculation should consider the month length
        }

        return "$pYear-${pMonth.toString().padStart(2, '0')}-${pDay.toString().padStart(2, '0')}"
    }