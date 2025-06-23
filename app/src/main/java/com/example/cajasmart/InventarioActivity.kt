package com.example.cajasmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cajasmart.databinding.ActivityInventarioBinding

class InventarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInventarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí va la lógica para:
        // - Mostrar lista de productos
        // - Agregar, editar y eliminar productos
        // - Calcular precio sugerido (80% de ganancia)
        // - Guardar productos en la base de datos
    }
}