package com.example.cajasmart.data

import androidx.room.*

@Dao
interface CorteDao {
    @Query("SELECT * FROM cortes")
    suspend fun getAll(): List<Corte>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(corte: Corte)

    @Update
    suspend fun update(corte: Corte)

    @Delete
    suspend fun delete(corte: Corte)
}