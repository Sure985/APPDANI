package com.example.cajasmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cajasmart.databinding.ActivityCorteCajaBinding

class CorteCajaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCorteCajaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteCajaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí va la lógica para:
        // - Mostrar ventas por día, semana, mes
        // - Mostrar totales, gastos, ganancias
        // - Ver estadísticas semanales (producto más vendido)
    }
}