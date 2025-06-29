package com.example.cajasmart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cajasmart.adapters.ProductoAdapter
import com.example.cajasmart.databinding.FragmentInventarioBinding
import com.example.cajasmart.viewmodel.InventarioViewModel
import com.example.cajasmart.viewmodel.InventarioViewModelFactory

class InventarioFragment : Fragment() {

    private var _binding: FragmentInventarioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InventarioViewModel by viewModels {
        InventarioViewModelFactory((requireActivity().application as App).db.productoDao())
    }

    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductoAdapter(
            productos = emptyList(),
            onEdit = { producto -> /* Acción editar */ },
            onDelete = { producto -> /* Acción borrar */ }
        )
        binding.recyclerInventario.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerInventario.adapter = adapter

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.actualizarLista(productos)
        }

        viewModel.cargarProductos()

        binding.etBuscarProducto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Solo llama si existe el método buscarProductos en el ViewModel
                viewModel.buscarProductos(s.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}