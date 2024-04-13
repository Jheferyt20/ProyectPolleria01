package com.app.proyectpolleria.Negocio;


import com.app.proyectpolleria.Datos.FavortioDatos;

public class FavoritoNegocio {

    FavortioDatos datosFavoritos = new FavortioDatos();

    public boolean registrarFavorito(int usuarioId, int platoId , StringBuilder mensaje){
        return datosFavoritos.registrarFavorito(usuarioId,platoId , mensaje);
    }

    public boolean eliminarFavorito(int idFavorito , StringBuilder mensaje){
        return datosFavoritos.eliminarFavorito(idFavorito ,mensaje );
    }

    public boolean eliminarFavorito2(int idUsuario , int idPlato , StringBuilder mensaje){
        return datosFavoritos.eliminarFavorito2(idUsuario , idPlato ,mensaje );
    }

    public boolean verificarFavorito(int idUsuario, int idPlato){
        return  datosFavoritos.verificarFavorito(idUsuario,idPlato);
    }


}
