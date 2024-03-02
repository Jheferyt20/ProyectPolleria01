package com.app.proyectpolleria.ui.micuenta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MicuentaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is micuenta Fragment"
    }
    val text: LiveData<String> = _text
}
