package com.example.cajasmart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.data.Producto
import com.example.cajasmart.databinding.ItemProductoBinding

class ProductoVentaAdapter(
    private var productos: List<Producto>,
    private val onAddToSale: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoVentaAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.binding.tvNombre.text = producto.nombre
        holder.binding.tvCantidad.text = "Disp: ${producto.cantidad}"
        holder.binding.tvPrecio.text = "$${producto.precio}"
        holder.itemView.setOnClickListener {
            onAddToSale(producto)
        }
    }

    override fun getItemCount() = productos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }
}