package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert

@Dao
interface VentaDao {
    @Insert
    suspend fun insertar(venta: Venta): Long

    @Query("SELECT COUNT(*) FROM ventas WHERE fecha BETWEEN :inicioDia AND :finDia")
    suspend fun getCantidadVentasHoy(inicioDia: Long, finDia: Long): Int

    @Query("SELECT IFNULL(SUM(total), 0) FROM ventas WHERE fecha BETWEEN :inicioDia AND :finDia")
    suspend fun getTotalVentasHoy(inicioDia: Long, finDia: Long): Double

    @Query("SELECT * FROM ventas WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun ventasEnRango(inicio: Long, fin: Long): List<Venta>

    @Query("SELECT SUM(total) FROM ventas WHERE fecha >= :desde")
    suspend fun totalVentasDesde(desde: Long): Double?

    @Query("DELETE FROM ventas WHERE fecha BETWEEN :inicio AND :fin")
    suspend fun eliminarVentasEnRango(inicio: Long, fin: Long)
}