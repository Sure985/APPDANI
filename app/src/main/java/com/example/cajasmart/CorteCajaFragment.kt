package com.example.cajasmart.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cajasmart.R
import com.example.cajasmart.data.AppDatabase
import com.example.cajasmart.data.Producto
import com.example.cajasmart.viewmodel.CorteCajaViewModel
import com.example.cajasmart.viewmodel.CorteCajaViewModelFactory

class CorteCajaFragment : Fragment(R.layout.fragment_corte_caja) {

    private val viewModel: CorteCajaViewModel by viewModels {
        val db = AppDatabase.getDatabase(requireContext())
        CorteCajaViewModelFactory(db.ventaDao(), db.detalleVentaDao())
    }

    private fun obtenerFechaUltimoCorte(): Long = 0L // Cambia si quieres cortes por fecha

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtVentas = view.findViewById<TextView>(R.id.textViewVentas)
        val txtTotal = view.findViewById<TextView>(R.id.textViewTotal)
        val txtProductos = view.findViewById<TextView>(R.id.textViewProductos)
        val btnVenta = view.findViewById<Button>(R.id.btnHacerVenta)

        // Observar resumen del corte
        viewModel.resumen.observe(viewLifecycleOwner) { resumen ->
            txtVentas.text = "Ventas realizadas: ${resumen.numeroVentas}"
            txtTotal.text = "Total vendido: ${resumen.total}"
            txtProductos.text = resumen.productosVendidos.joinToString("\n") {
                "${it.nombre}: ${it.total_vendidos}"
            }
        }

        // Cargar corte al iniciar
        viewModel.cargarCorte(obtenerFechaUltimoCorte())

        // Botón para simular una venta
        btnVenta.setOnClickListener {
            // Simula 2 productos vendidos
            val producto1 = Producto(id = 1, nombre = "Refresco", cantidad = 100, precio = 20.0)
            val producto2 = Producto(id = 2, nombre = "Papas", cantidad = 100, precio = 15.0)
            // Debes insertar los productos en la base de datos previamente para que existan.
            viewModel.registrarVenta(
                listOf(
                    producto1 to 2, // 2 refrescos
                    producto2 to 3  // 3 papas
                )
            )
            // Recarga el corte después de la venta
            viewModel.cargarCorte(obtenerFechaUltimoCorte())
        }
    }
}