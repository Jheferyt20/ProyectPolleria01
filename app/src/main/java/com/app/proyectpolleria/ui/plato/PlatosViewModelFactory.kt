package com.app.proyectpolleria.ui.plato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlatosViewModelFactory(private val itemId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlatosViewModel::class.java)) {
            return PlatosViewModel(itemId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


