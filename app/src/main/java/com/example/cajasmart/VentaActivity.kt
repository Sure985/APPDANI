package com.example.cajasmart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cajasmart.adapter.ProductoVentaAdapter
import com.example.cajasmart.adapter.ProductoEnVenta
import com.example.cajasmart.adapter.VentaActualAdapter
import com.example.cajasmart.data.Producto
import com.example.cajasmart.databinding.ActivityVentaBinding
import com.example.cajasmart.viewmodel.InventarioViewModel
import com.example.cajasmart.viewmodel.InventarioViewModelFactory

class VentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVentaBinding
    private val viewModel: InventarioViewModel by viewModels {
        InventarioViewModelFactory((application as App).db.productoDao())
    }

    private lateinit var productosAdapter: ProductoVentaAdapter
    private lateinit var ventaAdapter: VentaActualAdapter

    private val productosEnVenta = mutableListOf<ProductoEnVenta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productosAdapter = ProductoVentaAdapter(emptyList()) { producto ->
            agregarProductoAVenta(producto)
        }
        binding.recyclerProductosVenta.layoutManager = LinearLayoutManager(this)
        binding.recyclerProductosVenta.adapter = productosAdapter

        ventaAdapter = VentaActualAdapter(productosEnVenta) { prodEnVenta ->
            productosEnVenta.remove(prodEnVenta)
            ventaAdapter.actualizarLista(productosEnVenta)
            actualizarTotal()
        }
        binding.recyclerVentaActual.layoutManager = LinearLayoutManager(this)
        binding.recyclerVentaActual.adapter = ventaAdapter

        viewModel.productos.observe(this) { productos ->
            productosAdapter.actualizarLista(productos.filter { it.cantidad > 0 })
        }

        // Detecta cambios en el campo de pago para actualizar el cambio en tiempo real
        binding.etPagoCliente.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                actualizarCambio()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnFinalizarVenta.setOnClickListener {
            if (productosEnVenta.isEmpty()) {
                Toast.makeText(this, "Agrega productos a la venta", Toast.LENGTH_SHORT).show()
            } else {
                val total = productosEnVenta.sumOf { it.producto.precio * it.cantidad }
                val pagoCliente = binding.etPagoCliente.text.toString().toDoubleOrNull() ?: 0.0
                if (pagoCliente < total) {
                    Toast.makeText(this, "El pago es insuficiente", Toast.LENGTH_SHORT).show()
                } else {
                    finalizarVenta()
                    mostrarCambio()
                }
            }
        }

        viewModel.cargarProductos()
        actualizarCambio()
    }

    private fun agregarProductoAVenta(producto: Producto) {
        val existente = productosEnVenta.find { it.producto.id == producto.id }
        if (existente != null) {
            if (existente.cantidad < producto.cantidad) {
                existente.cantidad += 1
                ventaAdapter.actualizarLista(productosEnVenta)
                actualizarTotal()
            } else {
                Toast.makeText(this, "No hay más stock disponible", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (producto.cantidad > 0) {
                productosEnVenta.add(ProductoEnVenta(producto, 1))
                ventaAdapter.actualizarLista(productosEnVenta)
                actualizarTotal()
            }
        }
    }

    private fun actualizarTotal() {
        val total = productosEnVenta.sumOf { it.producto.precio * it.cantidad }
        binding.tvTotalVenta.text = "Total: $%.2f".format(total)
        actualizarCambio()
    }

    private fun actualizarCambio() {
        val total = productosEnVenta.sumOf { it.producto.precio * it.cantidad }
        val pagoCliente = binding.etPagoCliente.text.toString().toDoubleOrNull() ?: 0.0
        val cambio = if (pagoCliente >= total) pagoCliente - total else 0.0
        binding.tvCambio.text = "Cambio: $%.2f".format(cambio)
    }

    private fun mostrarCambio() {
        val total = productosEnVenta.sumOf { it.producto.precio * it.cantidad }
        val pagoCliente = binding.etPagoCliente.text.toString().toDoubleOrNull() ?: 0.0
        val cambio = pagoCliente - total
        binding.tvCambio.text = "Cambio: $%.2f".format(cambio)
        Toast.makeText(this, "Cambio: $%.2f".format(cambio), Toast.LENGTH_LONG).show()
    }

    private fun finalizarVenta() {
        productosEnVenta.forEach { prodEnVenta ->
            val nuevoStock = prodEnVenta.producto.cantidad - prodEnVenta.cantidad
            if (nuevoStock >= 0) {
                val prodActualizado = prodEnVenta.producto.copy(cantidad = nuevoStock)
                viewModel.actualizarProducto(prodActualizado)
            }
        }
        Toast.makeText(this, "¡Venta realizada con éxito!", Toast.LENGTH_SHORT).show()
        productosEnVenta.clear()
        ventaAdapter.actualizarLista(productosEnVenta)
        actualizarTotal()
        binding.etPagoCliente.setText("")
    }
}