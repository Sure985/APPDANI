package com.example.cajasmart.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.R

class VentaActualAdapter(
    private val onRemove: (ProductoEnVenta) -> Unit,
    private val onPrecioModificado: () -> Unit
) : RecyclerView.Adapter<VentaActualAdapter.VentaActualViewHolder>() {

    private val productos = mutableListOf<ProductoEnVenta>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaActualViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_venta_actual, parent, false)
        return VentaActualViewHolder(view)
    }

    override fun onBindViewHolder(holder: VentaActualViewHolder, position: Int) {
        val productoEnVenta = productos[position]
        holder.tvNombre.text = productoEnVenta.producto.nombre
        holder.tvCantidad.text = "Cantidad: ${productoEnVenta.cantidad}"

        // Remueve el watcher anterior si existe
        holder.etPrecio.removeTextChangedListener(holder.textWatcher)

        // Setea el precio en el EditText
        holder.etPrecio.setText("%.2f".format(productoEnVenta.precioUnitario))

        // Crea un nuevo watcher para actualizar el precio y subtotal
        holder.textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val nuevoPrecio = s.toString().toDoubleOrNull()
                if (nuevoPrecio != null) {
                    productoEnVenta.precioUnitario = nuevoPrecio
                    holder.tvSubtotal.text = "Subtotal: \$%.2f".format(nuevoPrecio * productoEnVenta.cantidad)
                    onPrecioModificado()  // << aquí avisas que hubo cambio para actualizar total
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // Añade el watcher al EditText
        holder.etPrecio.addTextChangedListener(holder.textWatcher)

        // Setea el subtotal inicial
        holder.tvSubtotal.text = "Subtotal: \$%.2f".format(productoEnVenta.precioUnitario * productoEnVenta.cantidad)

        holder.itemView.setOnLongClickListener {
            onRemove(productoEnVenta)
            true
        }
    }

    override fun getItemCount() = productos.size

    fun actualizarLista(nuevaLista: List<ProductoEnVenta>) {
        productos.clear()
        productos.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    class VentaActualViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreVenta)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidadVenta)
        val etPrecio: EditText = itemView.findViewById(R.id.etPrecioVenta)
        val tvSubtotal: TextView = itemView.findViewById(R.id.tvSubtotalVenta)

        // Guarda el watcher actual para poder removerlo
        var textWatcher: TextWatcher? = null
    }
}