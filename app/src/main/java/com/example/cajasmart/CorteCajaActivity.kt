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
            (application as App).db.detalleVentaDao(),
            (application as App).db.corteDao(),
            (application as App).db.productoCorteDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.resumen.observe(this) { resumen ->
            binding.tvVentasDelDia.text = "Ventas del día: ${resumen.numeroVentas}"
            binding.tvTotalDelDia.text = "Total vendido: $%.2f".format(resumen.total)
        }

        binding.btnRealizarCorte.setOnClickListener {
            viewModel.realizarCorte()
            Toast.makeText(this, "Corte realizado correctamente", Toast.LENGTH_SHORT).show()
            viewModel.cargarCorteDelDia()  // Recarga el resumen actualizado
        }

        binding.btnLimpiarCorte.setOnClickListener {
            viewModel.limpiarCorteActual()
            Toast.makeText(this, "Corte del día eliminado", Toast.LENGTH_SHORT).show()
        }

        viewModel.cargarCorteDelDia()
    }
}
