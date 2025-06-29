package com.example.cajasmart

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.adapters.CortesAdapter
import com.example.cajasmart.adapters.ProductosCorteAdapter
import com.example.cajasmart.data.AppDatabase
import com.example.cajasmart.data.Corte
import kotlinx.coroutines.launch

class ListaCortesFragment : Fragment(R.layout.fragment_lista_cortes) {

    private lateinit var recyclerCortes: RecyclerView
    private lateinit var recyclerProductos: RecyclerView
    private lateinit var tvTituloProductos: TextView

    private lateinit var adapterCortes: CortesAdapter
    private lateinit var adapterProductos: ProductosCorteAdapter

    private val cortes = mutableListOf<Corte>()
    private val db by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerCortes = view.findViewById(R.id.rvCortes)
        recyclerProductos = view.findViewById(R.id.rvProductosVendidos)
        tvTituloProductos = view.findViewById(R.id.tvTituloProductos)

        recyclerCortes.layoutManager = LinearLayoutManager(requireContext())
        recyclerProductos.layoutManager = LinearLayoutManager(requireContext())

        adapterCortes = CortesAdapter(cortes) { corteSeleccionado ->
            mostrarProductosDelCorte(corteSeleccionado.id)
        }
        recyclerCortes.adapter = adapterCortes

        // Carga los cortes
        lifecycleScope.launch {
            val listaCortes = db.corteDao().obtenerTodosLosCortes()
            cortes.clear()
            cortes.addAll(listaCortes)
            adapterCortes.notifyDataSetChanged()
        }
    }

    private fun mostrarProductosDelCorte(corteId: Int) {
        lifecycleScope.launch {
            val productos = db.productoCorteDao().obtenerProductosPorCorte(corteId)
            adapterProductos = ProductosCorteAdapter(productos) {}
            recyclerProductos.adapter = adapterProductos

            tvTituloProductos.visibility = View.VISIBLE
            recyclerProductos.visibility = View.VISIBLE
        }
    }
}
