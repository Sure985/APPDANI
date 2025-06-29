package com.example.cajasmart.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "cortes")
data class Corte(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: Long,           // Timestamp del día del corte
    val numeroVentas: Int,
    val totalVendido: Double
)

@Entity(
    tableName = "productos_corte",
    primaryKeys = ["corteId", "productoId"],
    foreignKeys = [
        ForeignKey(
            entity = Corte::class,
            parentColumns = ["id"],
            childColumns = ["corteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductoCorte(
    val corteId: Int,
    val productoId: Int,
    val nombreProducto: String,
    val cantidadVendida: Int
)
