package com.example.cajasmart.viewmodel

import androidx.lifecycle.*
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
    fun buscarProductos(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _productos.value = productoDao.getAll()
            } else {
                _productos.value = productoDao.buscarPorNombre("%$query%")
            }
        }
    }
    fun insertar(producto: Producto) {
        viewModelScope.launch {
            productoDao.insert(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.update(producto)
            cargarProductos()
        }
    }

    fun borrarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.delete(producto)
            cargarProductos()
        }
    }
}