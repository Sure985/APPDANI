package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProducto(id: Int): Producto?

    @Query("SELECT * FROM productos")
    suspend fun getAll(): List<Producto>

    @Insert
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)
}