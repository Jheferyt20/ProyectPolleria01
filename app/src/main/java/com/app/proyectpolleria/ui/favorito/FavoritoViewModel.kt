package com.app.proyectpolleria.ui.favorito

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.proyectpolleria.Adapter.FavoritoAdapter
import com.app.proyectpolleria.Entidad.Categoria
import com.app.proyectpolleria.Entidad.Favorito
import com.app.proyectpolleria.Repositorio.CategoriaRepository
import com.app.proyectpolleria.Repositorio.FavoritoRepository

class FavoritoViewModel(private val context: Context) : ViewModel() {

    private val _favorito: MutableLiveData<List<Favorito>> = MutableLiveData()
    val favorito: LiveData<List<Favorito>> get() = _favorito

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val favoritoRepository = FavoritoRepository()
    val preferences = context.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
    val idUsuario = preferences.getInt("USUARIO", 0)

    fun inicializar(idUsuario: Int) {
        fetchFavorito(idUsuario)
    }

    private fun fetchFavorito( IdUsuario : Int) {
        favoritoRepository.getFavoritosFromDatabase(IdUsuario , object : FavoritoRepository.ListaFavoritoCall {
            override fun onSuccess(favorito : List<Favorito>) {
                _favorito.value = favorito
            }
            override fun onError(errorMessage: String) {
                Log.e("FavoritoViewModel", "Error al obtener la categorias desde la base de datos: $errorMessage")
                _errorMessage.value = errorMessage
            }
        })
    }

}