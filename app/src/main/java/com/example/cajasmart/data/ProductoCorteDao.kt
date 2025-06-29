package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoCorteDao {
    @Insert
    suspend fun insertarProductos(vararg productos: ProductoCorte)

    @Query("SELECT * FROM productos_corte WHERE corteId = :corteId")
    suspend fun obtenerProductosPorCorte(corteId: Int): List<ProductoCorte>
}