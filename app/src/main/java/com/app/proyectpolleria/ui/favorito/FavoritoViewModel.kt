package com.app.proyectpolleria.ui.favorito

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorito Fragment"
    }
    val text: LiveData<String> = _text
}