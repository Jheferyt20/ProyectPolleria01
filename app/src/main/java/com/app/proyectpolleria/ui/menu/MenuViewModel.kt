package com.app.proyectpolleria.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.app.proyectpolleria.Entidad.Categoria
import com.app.proyectpolleria.Entidad.Plato
import com.app.proyectpolleria.Repositorio.CategoriaRepository
import com.app.proyectpolleria.Repositorio.PlatoRepository

class MenuViewModel : ViewModel() {

    private val _categoria: MutableLiveData<List<Categoria>> = MutableLiveData()
    val categoria: LiveData<List<Categoria>> get() = _categoria

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val categoriaRepository = CategoriaRepository()

    init {
        fetchCategoria()
    }


    private fun fetchCategoria() {
        categoriaRepository.getCategoriaFromDatabase(object : CategoriaRepository.ListaCategoriaCall {
            override fun onSuccess(categoria : List<Categoria>) {
                _categoria.value = categoria
            }

            override fun onError(errorMessage: String) {
                Log.e("MenuViewModel", "Error al obtener la categorias desde la base de datos: $errorMessage")
                _errorMessage.value = errorMessage
            }
        })
    }

}
