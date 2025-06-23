package com.example.cajasmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cajasmart.databinding.ActivityEstadisticasBinding

class EstadisticasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEstadisticasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadisticasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejemplo de cómo mostrar datos en las vistas
        binding.textTotalVentas.text = "Total de ventas: $10,000.00"
        binding.textProductoMasVendido.text = "Producto más vendido: Refresco"
        binding.textTotalProductos.text = "Total de productos: 50"

        // Si tienes un botón para actualizar estadísticas
        binding.btnActualizarEstadisticas.setOnClickListener {
            // Aquí puedes recargar datos, hacer cálculos, etc.
        }
    }
}