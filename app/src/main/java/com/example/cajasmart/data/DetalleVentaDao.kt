package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetalleVentaDao {
    @Insert
    suspend fun insertar(detalle: DetalleVenta)

    @Query("""
        SELECT p.nombre, SUM(dv.cantidad) as total_vendidos
        FROM detalle_venta dv
        INNER JOIN productos p ON dv.productoId = p.id
        INNER JOIN ventas v ON dv.ventaId = v.id
        WHERE v.fecha >= :desde
        GROUP BY dv.productoId
    """)
    suspend fun productosVendidosDesde(desde: Long): List<ProductoVendido>
}

data class ProductoVendido(
    val nombre: String,
    val total_vendidos: Int
)