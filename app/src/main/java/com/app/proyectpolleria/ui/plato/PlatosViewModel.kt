package com.app.proyectpolleria.ui.plato

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.proyectpolleria.Entidad.Plato
import com.app.proyectpolleria.Repositorio.PlatoRepository

class PlatosViewModel(private val itemId: Int) : ViewModel() {
    private val _plato: MutableLiveData<List<Plato>> = MutableLiveData()
    val plato: LiveData<List<Plato>> get() = _plato

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val platoRepository = PlatoRepository()

    init {
        fetchPlatos(itemId)
    }

    private fun fetchPlatos(idUsuario: Int) {
        platoRepository.getPlatoFromDatabase(idUsuario, object : PlatoRepository.ListaPlatoCall {
            override fun onSuccess(platos: List<Plato>) {
                _plato.value = platos
            }

            override fun onError(errorMessage: String) {
                Log.e("PlatosViewModel", "Error al obtener platos desde la base de datos: $errorMessage")
                _errorMessage.value = errorMessage
            }
        })
    }
}
