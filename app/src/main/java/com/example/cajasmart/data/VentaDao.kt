package com.example.cajasmart.data

import androidx.room.*

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas")
    suspend fun getAll(): List<Venta>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venta: Venta)

    @Update
    suspend fun update(venta: Venta)

    @Delete
    suspend fun delete(venta: Venta)
}