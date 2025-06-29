package com.example.cajasmart.data

import androidx.room.*
@Dao
interface CorteDao {
    @Insert
    suspend fun insertarCorte(corte: Corte): Long

    @Query("SELECT * FROM cortes ORDER BY fecha DESC")
    suspend fun obtenerTodosLosCortes(): List<Corte>
}


