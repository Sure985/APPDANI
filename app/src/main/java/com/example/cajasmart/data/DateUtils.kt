package com.example.cajasmart.util

import java.util.Calendar

fun obtenerTimestampsDelDia(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()
    // Inicio del día
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val inicioDia = calendar.timeInMillis

    // Fin del día
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    val finDia = calendar.timeInMillis

    return Pair(inicioDia, finDia)
}