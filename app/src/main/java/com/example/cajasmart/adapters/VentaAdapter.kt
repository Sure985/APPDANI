package com.example.cajasmart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cajasmart.databinding.ItemVentaBinding

data class Venta(
    val folio: String,
    val fecha: String,
    val total: Double
)

class VentaAdapter(
    private val ventas: List<Venta>
) : RecyclerView.Adapter<VentaAdapter.VentaViewHolder>() {

    inner class VentaViewHolder(private val binding: ItemVentaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(venta: Venta) {
            binding.textFolio.text = "Folio: ${venta.folio}"
            binding.textFecha.text = "Fecha: ${venta.fecha}"
            binding.textTotal.text = "Total: $${venta.total}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val binding = ItemVentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        holder.bind(ventas[position])
    }

    override fun getItemCount(): Int = ventas.size
}