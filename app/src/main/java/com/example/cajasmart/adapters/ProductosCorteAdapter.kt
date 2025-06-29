package com.example.cajasmart.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.R
import com.example.cajasmart.data.Corte
import java.text.SimpleDateFormat
import java.util.*
import com.example.cajasmart.data.ProductoCorte

class ProductosCorteAdapter(
    private val productos: List<ProductoCorte>,
    private val onClick: (ProductoCorte) -> Unit
) : RecyclerView.Adapter<ProductosCorteAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombreProducto)
        val tvCantidad = itemView.findViewById<TextView>(R.id.tvCantidadVendida)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_corte, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombreProducto
        holder.tvCantidad.text = producto.cantidadVendida.toString()
        holder.itemView.setOnClickListener { onClick(producto) }
    }

    override fun getItemCount() = productos.size
}
