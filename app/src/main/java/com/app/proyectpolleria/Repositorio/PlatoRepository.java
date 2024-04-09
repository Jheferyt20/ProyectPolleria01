package com.app.proyectpolleria.Repositorio;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Plato;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlatoRepository {

    public void getPlatoFromDatabase(int idCategoria , final ListaPlatoCall callback) {
        List<Plato> platos = new ArrayList<>();

        try (Connection conexion = DB_Polleria.conectar();
             PreparedStatement pstmt = conexion.prepareStatement("SELECT * FROM Plato WHERE categoria_id = ?");
        ) {
            pstmt.setInt(1, idCategoria);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Plato plato = new Plato();
                plato.setId(rs.getInt("id_plato"));
                plato.setNombre(rs.getString("nombre"));
                plato.setImgPlato(rs.getString("img"));
                plato.setDescripcion(rs.getString("descripcion"));
                plato.setPrecio(rs.getBigDecimal("precio_unitario"));
                platos.add(plato);
            }

            callback.onSuccess(platos);
        } catch (SQLException e) {
            e.printStackTrace();
            callback.onError("Error al obtener la lista de platos desde la base de datos");
        }
    }



    public interface ListaPlatoCall {
        void onSuccess(List<Plato> plato);

        void onError(String errorMessage);
    }

}
