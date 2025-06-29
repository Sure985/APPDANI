package com.example.cajasmart.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.cajasmart.R
import android.text.Editable
import android.text.TextWatcher

class AgregarProductoDialog(
    val onProductoAgregado: (String, Int, Double, Int, Double) -> Unit
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_agregar_producto, null)
        val etNombre = view.findViewById<EditText>(R.id.etNombreProducto)
        val etCantidad = view.findViewById<EditText>(R.id.etCantidadDisponible)
        val etPrecioPaquete = view.findViewById<EditText>(R.id.etPrecioPaquete)
        val etPiezasPaquete = view.findViewById<EditText>(R.id.etPiezasPaquete)
        val tvPrecioSugerido = view.findViewById<TextView>(R.id.tvPrecioSugerido)

        // Actualizar precio sugerido al cambiar precio o piezas
        val actualizarPrecioSugerido = {
            val precioPaquete = etPrecioPaquete.text.toString().toDoubleOrNull() ?: 0.0
            val piezasPaquete = etPiezasPaquete.text.toString().toIntOrNull() ?: 1
            val precioSugerido = if (piezasPaquete > 0) (precioPaquete / piezasPaquete) * 1.8 else 0.0
            tvPrecioSugerido.text = "Precio sugerido: $%.2f".format(precioSugerido)
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarPrecioSugerido()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etPrecioPaquete.addTextChangedListener(watcher)
        etPiezasPaquete.addTextChangedListener(watcher)

        return AlertDialog.Builder(requireContext())
            .setTitle("Agregar producto")
            .setView(view)
            .setPositiveButton("Agregar") { _, _ ->
                val nombre = etNombre.text.toString()
                val cantidad = etCantidad.text.toString().toIntOrNull() ?: 0
                val precioPaquete = etPrecioPaquete.text.toString().toDoubleOrNull() ?: 0.0
                val piezasPaquete = etPiezasPaquete.text.toString().toIntOrNull() ?: 1
                val precioSugerido = if (piezasPaquete > 0) (precioPaquete / piezasPaquete) * 1.8 else 0.0
                onProductoAgregado(nombre, cantidad, precioPaquete, piezasPaquete, precioSugerido)
            }
            .setNegativeButton("Cancelar", null)
            .create()
    }
}