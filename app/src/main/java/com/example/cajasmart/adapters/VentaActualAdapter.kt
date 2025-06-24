package com.example.cajasmart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.data.Producto
import com.example.cajasmart.databinding.ItemProductoVentaBinding

// Modelo auxiliar para la venta actual
data class ProductoEnVenta(
    val producto: Producto,
    var cantidad: Int
)

class VentaActualAdapter(
    private var productosEnVenta: List<ProductoEnVenta>,
    private val onRemove: (ProductoEnVenta) -> Unit
) : RecyclerView.Adapter<VentaActualAdapter.VentaViewHolder>() {

    inner class VentaViewHolder(val binding: ItemProductoVentaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val binding = ItemProductoVentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val item = productosEnVenta[position]
        holder.binding.tvNombreVenta.text = item.producto.nombre
        holder.binding.tvCantidadVenta.text = "x${item.cantidad}"
        holder.binding.tvPrecioVenta.text = "$%.2f".format(item.producto.precio * item.cantidad)
        holder.itemView.setOnLongClickListener {
            onRemove(item)
            true
        }
    }

    override fun getItemCount() = productosEnVenta.size

    fun actualizarLista(nuevaLista: List<ProductoEnVenta>) {
        productosEnVenta = nuevaLista
        notifyDataSetChanged()
    }
}