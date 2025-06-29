package com.example.cajasmart.util

import java.util.Calendar

fun obtenerTimestampsDeFecha(year: Int, month: Int, day: Int): Pair<Long, Long> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1) // Calendar.MONTH es base 0
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val inicio = calendar.timeInMillis
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val fin = calendar.timeInMillis - 1
    return inicio to fin
}