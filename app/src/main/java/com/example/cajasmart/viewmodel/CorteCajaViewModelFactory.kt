package com.example.cajasmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cajasmart.data.VentaDao
import com.example.cajasmart.data.DetalleVentaDao
import com.example.cajasmart.data.CorteDao
import com.example.cajasmart.data.ProductoCorteDao

class CorteCajaViewModelFactory(
    private val ventaDao: VentaDao,
    private val detalleVentaDao: DetalleVentaDao,
    private val corteDao: CorteDao,
    private val productoCorteDao: ProductoCorteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CorteCajaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CorteCajaViewModel(ventaDao, detalleVentaDao, corteDao, productoCorteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}