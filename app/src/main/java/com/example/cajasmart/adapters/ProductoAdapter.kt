package com.example.cajasmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.R
import com.example.cajasmart.data.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val onEdit: (Producto) -> Unit,
    private val onDelete: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvCantidad.text = "Piezas: ${producto.cantidad}"
        holder.tvPrecioPaquete.text = "Precio paquete: \$${"%.2f".format(producto.precioPaquete)}"
        val precioSugerido = if (producto.piezasPaquete > 0) (producto.precioPaquete / producto.piezasPaquete) * 1.8 else 0.0
        holder.tvPrecioSugerido.text = "Precio sugerido: \$${"%.2f".format(precioSugerido)}"
        holder.btnEdit.setOnClickListener { onEdit(producto) }
        holder.btnDelete.setOnClickListener { onDelete(producto) }
        holder.tvPiezasPaquete.text = "Piezas por paquete: ${producto.piezasPaquete}"
    }

    override fun getItemCount() = productos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val tvPrecioPaquete: TextView = itemView.findViewById(R.id.tvPrecioPaquete)
        val tvPrecioSugerido: TextView = itemView.findViewById(R.id.tvPrecioSugerido)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val tvPiezasPaquete: TextView = itemView.findViewById(R.id.tvPiezasPaquete) // <--- agrega esto
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }
}