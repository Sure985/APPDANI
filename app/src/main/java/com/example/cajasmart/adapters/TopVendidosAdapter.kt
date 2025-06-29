package com.example.cajasmart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.data.ProductoVendido
import com.example.cajasmart.databinding.ItemProductoVendidoBinding

class ProductoVendidoAdapter(
    private var productos: List<ProductoVendido>
) : RecyclerView.Adapter<ProductoVendidoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductoVendidoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoVendidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.tvNombreProducto.text = producto.nombre
        holder.binding.tvCantidadVendida.text = producto.total_vendidos.toString()
    }

    fun updateList(newList: List<ProductoVendido>) {
        productos = newList
        notifyDataSetChanged()
    }
}