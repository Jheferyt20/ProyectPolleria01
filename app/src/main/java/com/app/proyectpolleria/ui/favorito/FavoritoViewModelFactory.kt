package com.app.proyectpolleria.ui.favorito

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoritoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritoViewModel::class.java)) {
            return FavoritoViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
