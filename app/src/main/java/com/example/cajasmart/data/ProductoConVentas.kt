package com.example.cajasmart.data

data class ProductoConVentas(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val precioPaquete: Double,
    val piezasPaquete: Int,
    val totalVendidos: Int
)