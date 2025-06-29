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

class CortesAdapter(
    private val cortes: List<Corte>,
    private val onClick: (Corte) -> Unit
) : RecyclerView.Adapter<CortesAdapter.CorteViewHolder>() {

    inner class CorteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFecha = itemView.findViewById<TextView>(R.id.tvFechaCorte)
        private val tvVentas = itemView.findViewById<TextView>(R.id.tvVentasCorte)
        private val tvTotalVendido = itemView.findViewById<TextView>(R.id.tvTotalVendido)

        fun bind(corte: Corte) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            tvFecha.text = sdf.format(Date(corte.fecha))
            tvVentas.text = "Ventas: ${corte.numeroVentas}"
            tvTotalVendido.text = "Total vendido: $%.2f".format(corte.totalVendido)
            itemView.setOnClickListener { onClick(corte) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_corte, parent, false)
        return CorteViewHolder(view)
    }

    override fun onBindViewHolder(holder: CorteViewHolder, position: Int) {
        holder.bind(cortes[position])
    }

    override fun getItemCount() = cortes.size
}
