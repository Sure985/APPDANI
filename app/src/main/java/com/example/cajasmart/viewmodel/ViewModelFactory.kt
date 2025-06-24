package com.example.cajasmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cajasmart.data.ProductoDao

class InventarioViewModelFactory(private val productoDao: ProductoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventarioViewModel::class.java)) {
            return InventarioViewModel(productoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}