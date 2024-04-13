package com.app.proyectpolleria.Datos;



import android.widget.Toast;

import com.app.proyectpolleria.Conexion.DB_Polleria;
import com.app.proyectpolleria.Entidad.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


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


    public int consultarpersonal(String correo) {
        try {
            Statement stm = DB_Polleria.conectar().createStatement();
            ResultSet rs = stm.executeQuery("SELECT id_usuario FROM Usuario WHERE correo = '" + correo + "'");
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0; // Retornar un valor por defecto si no se encuentra ningún usuario con ese correo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la consulta: " + e.getMessage());
        }
    }


    public List<Usuario> recibirDatos(int idUsuario) {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            Statement stm = DB_Polleria.conectar().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Usuario WHERE id_usuario = " + idUsuario);

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellido(rs.getString("apellidos"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setImg(rs.getString("img"));

                usuarios.add(usuario);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la consulta: " + e.getMessage());
        }

        return usuarios;
    }


    public int editarUsuario(Usuario obj, StringBuilder mensaje) {
        int idActualizado = 0;
        Connection conexion = null;
        try {
            DB_Polleria conexionBD = new DB_Polleria();
            conexion = conexionBD.conectar();
            if (conexion != null) {
                String sql = "{CALL editarUsuario(?, ?, ?, ?, ?, ?, ?, ?,?)}";

                try (CallableStatement cstmt = conexion.prepareCall(sql)) {
                    cstmt.setInt(1, obj.getId()); // ID del usuario a editar
                    cstmt.setString(2,obj.getUsuario());
                    cstmt.setString(3, obj.getNombre());
                    cstmt.setString(4, obj.getApellido());
                    cstmt.setString(5, obj.getTelefono());
                    cstmt.setString(6, obj.getCorreo());
                    cstmt.setString(7, obj.getDireccion());
                    cstmt.registerOutParameter(8, Types.INTEGER); // Para @Resultado
                    cstmt.registerOutParameter(9, Types.VARCHAR); // Para @Mensaje

                    cstmt.executeUpdate();

                    idActualizado = cstmt.getInt(8); // Obtener el valor de retorno para @Resultado
                    mensaje.setLength(0);
                    mensaje.append(cstmt.getString(9)); // Obtener el mensaje devuelto por el procedimiento
                }
            } else {
                mensaje.setLength(0);
                mensaje.append("No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            mensaje.setLength(0);
            mensaje.append(e.getMessage());
        } finally {
            // Asegúrate de cerrar la conexión en el bloque finally
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return idActualizado;
    }

    public boolean GuardarImagen(int ID, String url) {
        try {
            Connection conexion = DB_Polleria.conectar();
            if (conexion != null) {
                String consulta = "UPDATE Usuario SET img=? WHERE id_usuario=?";

                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setString(1, url);
                    pstmt.setInt(2, ID);

                    int filasAfectadas = pstmt.executeUpdate();

                    return filasAfectadas != 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }


}