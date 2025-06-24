package com.example.cajasmart.viewmodel

import androidx.lifecycle.*
import com.example.cajasmart.data.*
import kotlinx.coroutines.launch

data class ResumenCorte(
    val numeroVentas: Int,
    val total: Double,
    val productosVendidos: List<ProductoVendido>
)

class CorteCajaViewModel(
    private val ventaDao: VentaDao,
    private val detalleVentaDao: DetalleVentaDao
) : ViewModel() {
    private val _resumen = MutableLiveData(ResumenCorte(0, 0.0, emptyList()))
    val resumen: LiveData<ResumenCorte> = _resumen

    fun cargarCorte(desde: Long) {
        viewModelScope.launch {
            val ventas = ventaDao.ventasDesde(desde)
            val total = ventas.sumOf { it.total }
            val productos = detalleVentaDao.productosVendidosDesde(desde)
            _resumen.postValue(ResumenCorte(ventas.size, total, productos))
        }
    }

    // Registrar una venta con productos
    fun registrarVenta(
        productos: List<Pair<Producto, Int>>, // Producto y cantidad
        fecha: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            val totalVenta = productos.sumOf { it.first.precio * it.second }
            val venta = Venta(total = totalVenta, fecha = fecha)
            val ventaId = ventaDao.insertar(venta).toInt()
            productos.forEach { (producto, cantidad) ->
                val detalle = DetalleVenta(
                    ventaId = ventaId,
                    productoId = producto.id,
                    cantidad = cantidad,
                    subtotal = producto.precio * cantidad
                )
                detalleVentaDao.insertar(detalle)
            }
        }
    }
}

class CorteCajaViewModelFactory(
    private val ventaDao: VentaDao,
    private val detalleVentaDao: DetalleVentaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CorteCajaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CorteCajaViewModel(ventaDao, detalleVentaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}