package com.example.cajasmart.viewmodel

import com.example.cajasmart.data.ProductoVendido

data class ResumenCorte(
    val numeroVentas: Int,
    val total: Double,
    val productosVendidos: List<ProductoVendido>
)