package com.app.proyectpolleria.Datos;

import android.text.BoringLayout;

import com.app.proyectpolleria.Conexion.DB_Polleria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CarritoDatos {

    public boolean buscarCarrito(int idUsuario, int idPlato) {
        boolean existe = false;
        String sql = "{CALL  sp_VerificarCarrito(?, ?, ?)}";

        try (Connection connection = DB_Polleria.conectar();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.setInt(1, idUsuario);
            statement.setInt(2, idPlato);
            statement.registerOutParameter(3, java.sql.Types.BIT);

            statement.execute();

            existe = statement.getBoolean(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }



    public Boolean OperacionCarrito(int IdCliente, int IdPlato, Boolean Sumar, int cantidad, StringBuilder mensaje){
        Boolean resultado = false;
        Connection conexion = null;
        try {
            DB_Polleria conexionBD = new DB_Polleria();
            conexion = conexionBD.conectar();
            if (conexion != null) {
                String sql = "{CALL sp_OperacionCarrito(?, ?, ?, ?, ?,?)}";
                try (CallableStatement cstmt = conexion.prepareCall(sql)) {
                    cstmt.setInt(1, IdCliente);
                    cstmt.setInt(2, IdPlato);
                    cstmt.setBoolean(3, Sumar);
                    cstmt.setInt(4, cantidad);
                    cstmt.registerOutParameter(5, Types.VARCHAR); // Para @Mensaje
                    cstmt.registerOutParameter(6, Types.BOOLEAN); // Para @Resultado

                    cstmt.executeUpdate();
                    resultado = cstmt.getBoolean(6);
                    mensaje.setLength(0);
                    mensaje.append(cstmt.getString(5));
                }
            }else {
                mensaje.setLength(0);
                mensaje.append("No se pudo establecer la conexión a la base de datos.");
            }
        }catch (Exception e){
            mensaje.setLength(0);
            mensaje.append(e.getMessage());
        }
        return resultado;
    }


    private static final String DELETE_FROM_CARRITO = "DELETE FROM carrito WHERE id_carrito = ?";

    public boolean eliminarCarrito(int idCarrito) {
        try (Connection connection = DB_Polleria.conectar();
             PreparedStatement statement = connection.prepareStatement(DELETE_FROM_CARRITO)) {
            statement.setInt(1, idCarrito);
            int filasEliminadas = statement.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar elemento del carrito: " + e.getMessage());
            return false;
        }
    }



}
