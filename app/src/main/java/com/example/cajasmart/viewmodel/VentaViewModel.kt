package com.example.cajasmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cajasmart.data.Venta
import com.example.cajasmart.data.VentaDao
import kotlinx.coroutines.launch

class VentaViewModel(private val ventaDao: VentaDao): ViewModel() {

    private val _ventas = MutableLiveData<List<Venta>>()
    val ventas: LiveData<List<Venta>> = _ventas

    fun cargarVentas() {
        viewModelScope.launch {
            _ventas.value = ventaDao.getAll()
        }
    }

    fun insertar(venta: Venta) {
        viewModelScope.launch {
            ventaDao.insert(venta)
            cargarVentas()
        }
    }

    fun actualizar(venta: Venta) {
        viewModelScope.launch {
            ventaDao.update(venta)
            cargarVentas()
        }
    }

    fun eliminar(venta: Venta) {
        viewModelScope.launch {
            ventaDao.delete(venta)
            cargarVentas()
        }
    }
}