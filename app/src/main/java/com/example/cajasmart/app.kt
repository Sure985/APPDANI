package com.example.cajasmart

import android.app.Application
import com.example.cajasmart.data.AppDatabase

class App : Application() {
    val db: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}