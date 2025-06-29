package com.example.cajasmart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetalleVentaDao {
    @Insert
    suspend fun insertar(detalle: DetalleVenta)

    @Query("DELETE FROM detalle_venta WHERE ventaId IN (SELECT id FROM ventas WHERE fecha BETWEEN :inicio AND :fin)")
    suspend fun eliminarDetallesEnRango(inicio: Long, fin: Long)

    @Query("""
        SELECT * FROM detalle_venta
        WHERE ventaId IN (SELECT id FROM ventas WHERE fecha BETWEEN :inicio AND :fin)
    """)
    suspend fun detallesEnRango(inicio: Long, fin: Long): List<DetalleVenta>

    @Query("""
    SELECT dv.productoId AS productoId, p.nombre AS nombre, SUM(dv.cantidad) AS total_vendidos
    FROM detalle_venta dv
    INNER JOIN productos p ON dv.productoId = p.id
    INNER JOIN ventas v ON dv.ventaId = v.id
    WHERE v.fecha BETWEEN :inicio AND :fin
    GROUP BY dv.productoId, p.nombre
""")
    suspend fun productosVendidosEnRango(inicio: Long, fin: Long): List<ProductoVendido>
}