package com.example.cajasmart.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detalle_venta")
data class DetalleVenta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ventaId: Int,
    val productoId: Int,
    val cantidad: Int,
    val subtotal: Double, // cantidad * precio producto
    val precioSugerido: Double // <-- agregas este campo aquí
)