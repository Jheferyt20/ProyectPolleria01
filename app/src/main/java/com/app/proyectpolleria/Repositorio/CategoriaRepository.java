package com.app.proyectpolleria.Repositorio;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Categoria;
import com.app.proyectpolleria.Entidad.Plato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {

    public void getCategoriaFromDatabase(final CategoriaRepository.ListaCategoriaCall callback) {
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conexion = DB_Polleria.conectar();
             PreparedStatement pstmt = conexion.prepareStatement("select * from categoria_menu");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setImgCategoria(rs.getString("img"));
                categorias.add(categoria);
            }
            callback.onSuccess(categorias);
        } catch (SQLException e) {
            e.printStackTrace();
            callback.onError("Error al obtener la lista de platos desde la base de datos");
        }
    }

    public interface ListaCategoriaCall {
        void onSuccess(List<Categoria> categoria);

        void onError(String errorMessage);
    }

}
