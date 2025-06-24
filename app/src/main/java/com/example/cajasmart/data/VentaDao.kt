package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VentaDao {
    @Insert
    suspend fun insertar(venta: Venta): Long // Regresa el id de la venta

    @Query("SELECT * FROM ventas WHERE fecha >= :desde")
    suspend fun ventasDesde(desde: Long): List<Venta>

    @Query("SELECT SUM(total) FROM ventas WHERE fecha >= :desde")
    suspend fun totalVentasDesde(desde: Long): Double?
}