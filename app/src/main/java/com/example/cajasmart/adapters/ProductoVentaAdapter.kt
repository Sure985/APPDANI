package com.example.cajasmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.R
import com.example.cajasmart.data.Producto

class ProductoVentaAdapter(
    private var productos: List<Producto>,
    private val onAdd: (Producto, Double) -> Unit // Cambiado para aceptar también el precio sugerido
) : RecyclerView.Adapter<ProductoVentaAdapter.ProductoVentaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoVentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_venta, parent, false)
        return ProductoVentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoVentaViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvNombre.text = producto.nombre
        holder.tvCantidad.text = "Cantidad: ${producto.cantidad}"
        val precioSugerido = calcularPrecioSugerido(producto)
        holder.tvPrecio.text = "Precio sugerido: \$${"%.2f".format(precioSugerido)}"
        holder.itemView.setOnClickListener { onAdd(producto, precioSugerido) } // Pasa ambos argumentos
    }

    override fun getItemCount() = productos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }

    private fun calcularPrecioSugerido(producto: Producto): Double {
        // precio = precio del paquete, piezas = piezasPaquete
        return if (producto.piezasPaquete > 0) {
            val costoUnitario = producto.precioPaquete / producto.piezasPaquete
            costoUnitario * 1.8
        } else {
            0.0
        }
    }

    class ProductoVentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
    }
}