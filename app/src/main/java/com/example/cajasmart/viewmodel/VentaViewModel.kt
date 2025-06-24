package com.example.cajasmart.viewmodel

import androidx.lifecycle.*
import com.example.cajasmart.data.Venta
import com.example.cajasmart.data.VentaDao
import kotlinx.coroutines.launch

class VentaViewModel(private val ventaDao: VentaDao) : ViewModel() {

    private val _totalVentas = MutableLiveData<Double>()
    val totalVentas: LiveData<Double> = _totalVentas

    fun insertarVenta(venta: Venta, desde: Long) {
        viewModelScope.launch {
            ventaDao.insertar(venta)
            val total = ventaDao.totalVentasDesde(desde) ?: 0.0
            _totalVentas.postValue(total)
        }
    }

    fun cargarTotalVentasDesde(desde: Long) {
        viewModelScope.launch {
            val total = ventaDao.totalVentasDesde(desde) ?: 0.0
            _totalVentas.postValue(total)
        }
    }
}

class VentaViewModelFactory(private val ventaDao: VentaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VentaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VentaViewModel(ventaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}