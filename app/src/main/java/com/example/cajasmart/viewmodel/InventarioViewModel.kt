package com.example.cajasmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cajasmart.data.Producto
import com.example.cajasmart.data.ProductoDao
import kotlinx.coroutines.launch

class InventarioViewModel(private val productoDao: ProductoDao): ViewModel() {

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> = _productos

    fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = productoDao.getAll()
        }
    }

    fun insertar(producto: Producto) {
        viewModelScope.launch {
            productoDao.insert(producto)
            cargarProductos()
        }
    }

    fun actualizar(producto: Producto) {
        viewModelScope.launch {
            productoDao.update(producto)
            cargarProductos()
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            productoDao.delete(producto)
            cargarProductos()
        }
    }
}