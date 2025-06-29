package com.example.cajasmart.data

import androidx.room.*

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

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :query || '%'")
    suspend fun buscarPorNombre(query: String): List<Producto>

    // Productos ordenados por ventas
    @Query("""
        SELECT p.id, p.nombre, p.cantidad, p.precioPaquete, p.piezasPaquete, IFNULL(SUM(dv.cantidad), 0) as totalVendidos
        FROM productos p
        LEFT JOIN detalle_venta dv ON dv.productoId = p.id
        GROUP BY p.id
        ORDER BY totalVendidos DESC
    """)
    suspend fun getProductosOrdenadosPorVentas(): List<ProductoConVentas>
}