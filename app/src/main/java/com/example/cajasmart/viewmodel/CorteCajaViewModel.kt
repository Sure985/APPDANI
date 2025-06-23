package com.example.cajasmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cajasmart.data.Corte
import com.example.cajasmart.data.CorteDao
import kotlinx.coroutines.launch

class CorteCajaViewModel(private val corteDao: CorteDao): ViewModel() {

    private val _cortes = MutableLiveData<List<Corte>>()
    val cortes: LiveData<List<Corte>> = _cortes

    fun cargarCortes() {
        viewModelScope.launch {
            _cortes.value = corteDao.getAll()
        }
    }

    fun insertar(corte: Corte) {
        viewModelScope.launch {
            corteDao.insert(corte)
            cargarCortes()
        }
    }

    fun actualizar(corte: Corte) {
        viewModelScope.launch {
            corteDao.update(corte)
            cargarCortes()
        }
    }

    fun eliminar(corte: Corte) {
        viewModelScope.launch {
            corteDao.delete(corte)
            cargarCortes()
        }
    }
}