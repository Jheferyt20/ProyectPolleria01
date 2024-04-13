package com.app.proyectpolleria.Repositorio;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Carrito;
import com.app.proyectpolleria.Entidad.Plato;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarritoRepositorio {
    public void getCarritoFromDatabase(int idUsuario , final CarritoRepositorio.ListaCarritoCall callback) {
        List<Carrito> carritos = new ArrayList<>();

        try (Connection conexion = DB_Polleria.conectar();
             PreparedStatement pstmt = conexion.prepareStatement("select * from carrito where usuario_id = ?");
        ) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Carrito carrito = new Carrito();
                carrito.setId(rs.getInt("id_carrito"));
                carrito.setId_Usuario(rs.getInt("usuario_id"));
                carrito.setId_Plato(rs.getInt("plato_id"));
                carrito.setCantidad(rs.getInt("cantidad"));
                carritos.add(carrito);
            }
            callback.onSuccess(carritos);
        } catch (SQLException e) {
            e.printStackTrace();
            callback.onError("Error al obtener la lista de platos desde la base de datos");
        }
    }


    public interface ListaCarritoCall {
        void onSuccess(List<Carrito> plato);
        void onError(String errorMessage);
    }
}
