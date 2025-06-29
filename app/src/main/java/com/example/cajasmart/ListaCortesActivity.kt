package com.example.cajasmart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.adapters.CortesAdapter
import com.example.cajasmart.adapters.ProductosCorteAdapter
import com.example.cajasmart.data.AppDatabase
import kotlinx.coroutines.launch

class ListaCortesActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var cortesAdapter: CortesAdapter
    private lateinit var productosCorteAdapter: ProductosCorteAdapter
    private lateinit var recyclerCortes: RecyclerView
    private lateinit var recyclerProductosCorte: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_cortes) // El layout debe tener dos RecyclerView

        db = AppDatabase.getDatabase(this)

        recyclerCortes = findViewById(R.id.recyclerCortes)
        recyclerProductosCorte = findViewById(R.id.recyclerProductosCorte)

        recyclerCortes.layoutManager = LinearLayoutManager(this)
        recyclerProductosCorte.layoutManager = LinearLayoutManager(this)

        recyclerProductosCorte.visibility = View.GONE // Oculto al inicio

        cargarCortes()
    }

    private fun cargarCortes() {
        lifecycleScope.launch {
            val cortes = db.corteDao().obtenerTodosLosCortes()
            cortesAdapter = CortesAdapter(cortes) { corteSeleccionado ->
                mostrarProductosDelCorte(corteSeleccionado.id)
            }
            recyclerCortes.adapter = cortesAdapter
        }
    }

    private fun mostrarProductosDelCorte(corteId: Int) {
        lifecycleScope.launch {
            val productos = db.productoCorteDao().obtenerProductosPorCorte(corteId)
            productosCorteAdapter = ProductosCorteAdapter(productos) { /* aquí si quieres puedes manejar clicks en productos */ }
            recyclerProductosCorte.adapter = productosCorteAdapter
            recyclerProductosCorte.visibility = View.VISIBLE
        }
    }
}
