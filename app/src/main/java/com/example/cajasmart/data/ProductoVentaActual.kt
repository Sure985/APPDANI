package com.example.cajasmart.model

import com.example.cajasmart.data.Producto

data class ProductoVentaActual(
    val producto: Producto,
    var cantidad: Int = 1,
    var precioVenta: Double = 0.0 // Precio manual que ingresará el usuario
) {
    val precioSugerido: Double
        get() = (producto.precioPaquete / producto.piezasPaquete) * 1.8
}
