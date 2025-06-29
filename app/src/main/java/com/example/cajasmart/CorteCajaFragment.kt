package com.example.cajasmart.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cajasmart.R
import com.example.cajasmart.data.AppDatabase
import com.example.cajasmart.data.Producto
import com.example.cajasmart.viewmodel.CorteCajaViewModel
import com.example.cajasmart.viewmodel.CorteCajaViewModelFactory
import kotlinx.coroutines.launch

class CorteCajaFragment : Fragment(R.layout.activity_corte_caja) {

    private val db by lazy { AppDatabase.getDatabase(requireContext()) }

    // Configura el ViewModel con su Factory correctamente
    private val viewModel: CorteCajaViewModel by viewModels {
        CorteCajaViewModelFactory(
            db.ventaDao(),
            db.detalleVentaDao(),
            db.corteDao(),
            db.productoCorteDao()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtVentas = view.findViewById<TextView>(R.id.textViewVentas)
        val txtTotal = view.findViewById<TextView>(R.id.textViewTotal)
        val txtProductos = view.findViewById<TextView>(R.id.textViewProductos)
        val btnVenta = view.findViewById<Button>(R.id.btnHacerVenta)
        val btnLimpiar = view.findViewById<Button>(R.id.btnLimpiarCorte)
        val btnRealizarCorte = view.findViewById<Button>(R.id.btnRealizarCorte)

        // Observa el resumen del corte
        viewModel.resumen.observe(viewLifecycleOwner) { resumen ->
            txtVentas.text = "Ventas realizadas: ${resumen.numeroVentas}"
            txtTotal.text = "Total vendido: $%.2f".format(resumen.total)
            txtProductos.text = resumen.productosVendidos.joinToString("\n") {
                "${it.nombre}: ${it.total_vendidos}"
            }
        }

        // Cargar corte al iniciar
        viewModel.cargarCorteDelDia()
        btnRealizarCorte.setOnClickListener {
            viewModel.realizarCorte()
            Toast.makeText(requireContext(), "Corte realizado correctamente", Toast.LENGTH_SHORT).show()
            viewModel.cargarCorteDelDia()
        }
        // Botón para simular una venta
        btnVenta.setOnClickListener {
            lifecycleScope.launch {
                simularVentaConProductosExistentes()
            }
        }

        // Botón para limpiar el corte (realizar corte)
        btnLimpiar.setOnClickListener {
            viewModel.limpiarCorteActual()
            Toast.makeText(requireContext(), "Corte del día eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun simularVentaConProductosExistentes() {
        val productoDao = db.productoDao()

        // Productos que se usarán en la venta
        val producto1 = Producto(
            id = 1,
            nombre = "Refresco",
            cantidad = 100,
            precioPaquete = 120.0,
            piezasPaquete = 6
        )
        val producto2 = Producto(
            id = 2,
            nombre = "Botana",
            cantidad = 50,
            precioPaquete = 80.0,
            piezasPaquete = 8
        )

        // Inserta los productos si no existen aún
        if (productoDao.getProducto(1) == null) {
            productoDao.insert(producto1)
        }
        if (productoDao.getProducto(2) == null) {
            productoDao.insert(producto2)
        }

        // Registrar venta usando esos productos
        viewModel.registrarVenta(
            listOf(producto1 to 2, producto2 to 3),
            listOf(20.0, 10.0)
        )

        // Recargar corte después de venta
        viewModel.cargarCorteDelDia()
    }
}
