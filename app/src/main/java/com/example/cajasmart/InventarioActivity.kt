package com.example.cajasmart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cajasmart.adapter.ProductoAdapter
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

    // Variable para saber si estamos editando un producto
    private var productoEditando: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter con las funciones de editar y borrar
        adapter = ProductoAdapter(
            productos = emptyList(),
            onEdit = { producto ->
                binding.etNombre.setText(producto.nombre)
                binding.etCantidad.setText(producto.cantidad.toString())
                binding.etPrecio.setText(producto.precio.toString())
                productoEditando = producto
                binding.btnAgregar.text = "Editar producto"
            },
            onDelete = { producto ->
                viewModel.borrarProducto(producto)
            }
        )
        binding.recyclerInventario.layoutManager = LinearLayoutManager(this)
        binding.recyclerInventario.adapter = adapter

        // Observar los productos
        viewModel.productos.observe(this, Observer { productos ->
            adapter.actualizarLista(productos)
        })

        // Cargar productos al iniciar
        viewModel.cargarProductos()

        // Click en agregar o editar
        binding.btnAgregar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val cantidad = binding.etCantidad.text.toString().toIntOrNull() ?: 0
            val precio = binding.etPrecio.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotBlank()) {
                if (productoEditando != null) {
                    val productoActualizado = productoEditando!!.copy(
                        nombre = nombre,
                        cantidad = cantidad,
                        precio = precio
                    )
                    viewModel.actualizarProducto(productoActualizado)
                    productoEditando = null
                    binding.btnAgregar.text = "Agregar producto"
                } else {
                    val producto = Producto(nombre = nombre, cantidad = cantidad, precio = precio)
                    viewModel.insertar(producto)
                }
                binding.etNombre.text?.clear()
                binding.etCantidad.text?.clear()
                binding.etPrecio.text?.clear()
            }
        }
    }
}