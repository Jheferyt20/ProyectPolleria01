package com.app.proyectpolleria.Datos;



import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class UsuarioDatos {


    public  Boolean Login (String usuario , String contrasena) {
        ResultSet resultado;
        Connection conexion = null;
        try {
            DB_Polleria conectionBD = new DB_Polleria();
            conexion = conectionBD.conectar();
            if (conexion != null) {
                String consulta = "SELECT * FROM usuario   WHERE Correo = ? AND Contraseña = ?";

                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, usuario);
                    pstmt.setString(2, contrasena);

                    resultado = pstmt.executeQuery();

                    return resultado.next();
                }

            } else {
                return false;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }


    public int RegistrarUsuario(Usuario obj, StringBuilder mensaje) {
        int idGenerado = 0;
        Connection conexion = null;

        try {
            DB_Polleria conectionBD = new DB_Polleria();
            conexion = conectionBD.conectar();
            if (conexion != null) {
                String sql = "{CALL sp_RegistrarUsuario(?, ?, ?, ?, ?, ?, ?, ?)}";

                try (CallableStatement cstmt = conexion.prepareCall(sql)) {
                    cstmt.setString(1, obj.getUsuario());
                    cstmt.setString(2, obj.getNombre());
                    cstmt.setString(3, obj.getApellido());
                    cstmt.setString(4, obj.getCorreo());
                    cstmt.setString(5, obj.getClave());
                    cstmt.setString(6, obj.getTelefono());
                    cstmt.registerOutParameter(7, Types.VARCHAR);
                    cstmt.registerOutParameter(8, Types.INTEGER);

                    cstmt.executeUpdate();

                    idGenerado = cstmt.getInt(8);
                    mensaje.setLength(0); // Limpiar el StringBuilder antes de asignarle el nuevo mensaje
                    mensaje.append(cstmt.getString(7)); // Asignar el mensaje devuelto por el procedimiento

                }
            } else {
                mensaje.setLength(0); // Limpiar el StringBuilder antes de asignarle el mensaje de error
                mensaje.append("No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            mensaje.setLength(0); // Limpiar el StringBuilder antes de asignarle el mensaje de error
            mensaje.append(e.getMessage());
        }

        return idGenerado;
    }


    public int RegistrarUsuarioGoogle(Usuario obj, StringBuilder mensaje) {
        int idGenerado = 0;
        Connection conexion = null;
        try {
            DB_Polleria conexionBD = new DB_Polleria();
            conexion = conexionBD.conectar();
            if (conexion != null) {
                String sql = "{CALL registra_con_google(?, ?, ?, ?, ?, ?)}";

                try (CallableStatement cstmt = conexion.prepareCall(sql)) {
                    cstmt.setString(1, obj.getNombre());
                    cstmt.setString(2, obj.getApellido());
                    cstmt.setString(3, obj.getCorreo());
                    cstmt.setString(4, obj.getImg());
                    cstmt.registerOutParameter(5, Types.VARCHAR);
                    cstmt.registerOutParameter(6, Types.INTEGER);

                    cstmt.executeUpdate();

                    idGenerado = cstmt.getInt(6);
                    mensaje.setLength(0);
                    mensaje.append(cstmt.getString(5));

                }
            } else {
                mensaje.setLength(0);
                mensaje.append("No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            mensaje.setLength(0);
            mensaje.append(e.getMessage());
        }
        return idGenerado;
    }




}