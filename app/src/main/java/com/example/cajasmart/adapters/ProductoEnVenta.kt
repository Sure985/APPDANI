package com.example.cajasmart.adapters

import com.example.cajasmart.data.Producto

data class ProductoEnVenta(
    val producto: Producto,
    var cantidad: Int,
    var precioUnitario: Double // precio sugerido por pieza
)