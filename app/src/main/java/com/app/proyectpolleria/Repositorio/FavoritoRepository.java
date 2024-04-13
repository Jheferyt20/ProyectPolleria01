package com.app.proyectpolleria.Repositorio;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Categoria;
import com.app.proyectpolleria.Entidad.Favorito;
import com.app.proyectpolleria.Entidad.Plato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritoRepository {
    public void getFavoritosFromDatabase(int idUsuario , final FavoritoRepository.ListaFavoritoCall callback) {
        List<Favorito> favoritos = new ArrayList<>();

        try (Connection conexion = DB_Polleria.conectar();
             PreparedStatement pstmt = conexion.prepareStatement("select * from favorito where usuario_id = ?")) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Favorito favorito = new Favorito();
                favorito.setIdFavorito(rs.getInt("id_favorito"));
                favorito.setIdUsuario(rs.getInt("usuario_id"));
                favorito.setIdPlato(rs.getInt("plato_id"));
                favoritos.add(favorito);
            }

            callback.onSuccess(favoritos);
        } catch (SQLException e) {
            e.printStackTrace();
            callback.onError("Error al obtener la lista de favoritos desde la base de datos");
        }
    }

    public interface ListaFavoritoCall {
        void onSuccess(List<Favorito> favoritos);

        void onError(String errorMessage);
    }

}
