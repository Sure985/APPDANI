package com.example.cajasmart.viewmodel

import androidx.lifecycle.*
import com.example.cajasmart.data.*
import com.example.cajasmart.util.obtenerTimestampsDeFecha
import kotlinx.coroutines.launch
import java.util.Calendar
import  com.example.cajasmart.data.ProductoCorteDao

class CorteCajaViewModel(
    private val ventaDao: VentaDao,
    private val detalleVentaDao: DetalleVentaDao,
    private val corteDao: CorteDao,
    private val productoCorteDao: ProductoCorteDao
) : ViewModel() {

    private val _resumen = MutableLiveData(ResumenCorte(0, 0.0, emptyList()))
    val resumen: LiveData<ResumenCorte> = _resumen

    private var fechaSeleccionada: Triple<Int, Int, Int> = getHoy()

    fun registrarVenta(
        productos: List<Pair<Producto, Int>>,
        preciosSugeridos: List<Double>,
        fecha: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            val totalVenta = productos.indices.sumOf { i ->
                preciosSugeridos[i] * productos[i].second
            }
            val venta = Venta(total = totalVenta, fecha = fecha)
            val ventaId = ventaDao.insertar(venta)
            productos.forEachIndexed { index, (producto, cantidad) ->
                val precioSugerido = preciosSugeridos[index]
                val detalle = DetalleVenta(
                    ventaId = ventaId.toInt(),
                    productoId = producto.id,
                    cantidad = cantidad,
                    subtotal = cantidad * precioSugerido,
                    precioSugerido = precioSugerido
                )
                detalleVentaDao.insertar(detalle)
            }
            cargarCorteActual()
        }
    }

    fun limpiarCorteActual() {
        viewModelScope.launch {
            val (inicio, fin) = obtenerTimestampsDeFecha(
                fechaSeleccionada.first,
                fechaSeleccionada.second,
                fechaSeleccionada.third
            )
            detalleVentaDao.eliminarDetallesEnRango(inicio, fin)
            ventaDao.eliminarVentasEnRango(inicio, fin)
            cargarCorteActual()
        }
    }

    fun cargarCorteActual() {
        cargarCorteDeFecha(
            fechaSeleccionada.first,
            fechaSeleccionada.second,
            fechaSeleccionada.third
        )
    }

    fun cargarCorteDelDia() {
        val hoy = getHoy()
        fechaSeleccionada = hoy
        cargarCorteDeFecha(hoy.first, hoy.second, hoy.third)
    }

    fun cargarCorteDeFecha(year: Int, month: Int, day: Int) {
        fechaSeleccionada = Triple(year, month, day)
        viewModelScope.launch {
            val (inicio, fin) = obtenerTimestampsDeFecha(year, month, day)
            val ventas = ventaDao.ventasEnRango(inicio, fin)
            val detalles = detalleVentaDao.detallesEnRango(inicio, fin)
            val productosVendidos = detalleVentaDao.productosVendidosEnRango(inicio, fin)
            val total = detalles.sumOf { it.precioSugerido * it.cantidad }
            _resumen.postValue(
                ResumenCorte(
                    ventas.size,
                    total,
                    productosVendidos
                )
            )
        }
    }

    fun realizarCorte() {
        viewModelScope.launch {
            val resumenActual = resumen.value ?: return@launch
            val (inicio, fin) = obtenerTimestampsDeFecha(
                fechaSeleccionada.first,
                fechaSeleccionada.second,
                fechaSeleccionada.third
            )
            // Guardar corte con la fecha inicio del día
            val idCorte = corteDao.insertarCorte(
                Corte(
                    fecha = inicio,
                    numeroVentas = resumenActual.numeroVentas,
                    totalVendido = resumenActual.total
                )
            )
            // Guardar productos vendidos para ese corte
            val productosParaInsertar = resumenActual.productosVendidos.map {
                ProductoCorte(
                    corteId = idCorte.toInt(),
                    productoId = it.productoId,
                    nombreProducto = it.nombre,
                    cantidadVendida = it.total_vendidos
                )
            }
            productoCorteDao.insertarProductos(*productosParaInsertar.toTypedArray())

            // Limpiar ventas y detalles del día
            limpiarCorteActual()
        }
    }

    private fun getHoy(): Triple<Int, Int, Int> {
        val cal = Calendar.getInstance()
        return Triple(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1, // Ajuste: meses base 0 a base 1
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }
}
