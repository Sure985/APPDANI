package com.example.cajasmart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cajasmart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVenta.setOnClickListener {
            startActivity(Intent(this, VentaActivity::class.java))
        }
        binding.btnInventario.setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }
        binding.btnCorteCaja.setOnClickListener {
            startActivity(Intent(this, CorteCajaActivity::class.java))
        }
    }
}