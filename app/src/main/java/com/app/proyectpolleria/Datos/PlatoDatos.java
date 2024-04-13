package com.app.proyectpolleria.Datos;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Plato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlatoDatos {

    public List<Plato> ListarPlato(int idPlato) {
        List<Plato> platos = new ArrayList<>();
        try (Connection conexion = DB_Polleria.conectar();
             PreparedStatement pstmt = conexion.prepareStatement("SELECT * FROM Plato WHERE id_plato = ?");) {
            pstmt.setInt(1, idPlato);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platos;
    }

}
