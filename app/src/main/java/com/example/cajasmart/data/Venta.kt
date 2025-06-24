package com.example.cajasmart.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val total: Double,
    val fecha: Long // Guarda la fecha como timestamp (System.currentTimeMillis())
)