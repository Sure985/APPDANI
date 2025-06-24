package com.example.cajasmart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cajasmart.databinding.ActivityCorteCajaBinding
import com.example.cajasmart.viewmodel.CorteCajaViewModel
import com.example.cajasmart.viewmodel.CorteCajaViewModelFactory

class CorteCajaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorteCajaBinding
    private val viewModel: CorteCajaViewModel by viewModels {
        CorteCajaViewModelFactory(
            (application as App).db.ventaDao(),
            (application as App).db.detalleVentaDao()
        )
    }

    private var ultimoCorte: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.cargarCorte(ultimoCorte)

        viewModel.resumen.observe(this) { resumen ->
            binding.tvVentas.text = "Ventas realizadas: ${resumen.numeroVentas}"
            binding.tvTotal.text = "Total vendido: $%.2f".format(resumen.total)
        }

        binding.btnCorte.setOnClickListener {
            ultimoCorte = System.currentTimeMillis()
            Toast.makeText(this, "Corte realizado. Listo para nuevo turno.", Toast.LENGTH_LONG).show()
            viewModel.cargarCorte(ultimoCorte)
        }
    }
}