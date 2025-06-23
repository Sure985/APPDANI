package com.example.cajasmart.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cortes")
data class Corte(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val total: Double
)