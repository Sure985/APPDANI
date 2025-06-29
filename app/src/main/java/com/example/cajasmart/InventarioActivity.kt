package com.example.cajasmart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cajasmart.adapters.ProductoAdapter
import com.example.cajasmart.data.Producto
import com.example.cajasmart.databinding.ActivityInventarioBinding
import com.example.cajasmart.viewmodel.InventarioViewModel
import com.example.cajasmart.viewmodel.InventarioViewModelFactory

class InventarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventarioBinding

    private val viewModel: InventarioViewModel by viewModels {
        InventarioViewModelFactory((application as App).db.productoDao())
    }

    private lateinit var adapter: ProductoAdapter
    private var productoEditando: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductoAdapter(
            productos = emptyList(),
            onEdit = { producto ->
                binding.etNombre.setText(producto.nombre)
                binding.etCantidad.setText(producto.cantidad.toString())
                binding.etPrecioPaquete.setText(producto.precioPaquete.toString())
                binding.etPiezasPaquete.setText(producto.piezasPaquete.toString())
                productoEditando = producto
                binding.btnAgregar.text = "Editar producto"
            },
            onDelete = { producto ->
                viewModel.borrarProducto(producto)
            }
        )
        binding.recyclerInventario.layoutManager = LinearLayoutManager(this)
        binding.recyclerInventario.adapter = adapter

        viewModel.productos.observe(this, Observer { productos ->
            adapter.actualizarLista(productos)
        })

        viewModel.cargarProductos()

        binding.btnAgregar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val cantidad = binding.etCantidad.text.toString().toIntOrNull() ?: 0
            val precioPaquete = binding.etPrecioPaquete.text.toString().toDoubleOrNull() ?: 0.0
            val piezasPaquete = binding.etPiezasPaquete.text.toString().toIntOrNull() ?: 1

            if (nombre.isNotBlank() && cantidad > 0 && precioPaquete > 0.0 && piezasPaquete > 0) {
                if (productoEditando != null) {
                    val productoActualizado = productoEditando!!.copy(
                        nombre = nombre,
                        cantidad = cantidad,
                        precioPaquete = precioPaquete,
                        piezasPaquete = piezasPaquete
                    )
                    viewModel.actualizarProducto(productoActualizado)
                    productoEditando = null
                    binding.btnAgregar.text = "Agregar producto"
                } else {
                    val producto = Producto(
                        nombre = nombre,
                        cantidad = cantidad,
                        precioPaquete = precioPaquete,
                        piezasPaquete = piezasPaquete
                    )
                    viewModel.insertar(producto)
                }
                binding.etNombre.text?.clear()
                binding.etCantidad.text?.clear()
                binding.etPrecioPaquete.text?.clear()
                binding.etPiezasPaquete.text?.clear()
            }
        }
    }
}