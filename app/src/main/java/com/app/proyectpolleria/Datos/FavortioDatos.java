package com.app.proyectpolleria.Datos;

import com.app.proyectpolleria.Conexion.DB_Polleria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class FavortioDatos {

    public boolean registrarFavorito(int usuarioId, int platoId,StringBuilder mensaje) {
        boolean resultado = false;

        try (Connection connection = DB_Polleria.conectar();
             CallableStatement statement = connection.prepareCall("{CALL sp_RegistrarFavorito(?, ?, ?, ?)}")) {

            statement.setInt(1, usuarioId);
            statement.setInt(2, platoId);
            statement.registerOutParameter(3, Types.BIT);
            statement.registerOutParameter(4, Types.VARCHAR);

            statement.execute();

            resultado = statement.getBoolean(3);
            mensaje.append(statement.getString(4));

            System.out.println(mensaje);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean eliminarFavorito(int idFavorito ,StringBuilder mensaje) {
        boolean resultado = false;

        try (Connection connection = DB_Polleria.conectar();
             CallableStatement statement = connection.prepareCall("{CALL sp_EliminarFavorito(?, ?, ?)}")) {

            statement.setInt(1, idFavorito);
            statement.registerOutParameter(2, Types.BIT);
            statement.registerOutParameter(3, Types.VARCHAR);

            statement.execute();

            resultado = statement.getBoolean(2);
            mensaje.append(statement.getString(3));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean eliminarFavorito2(int idUsuario,int idPlato ,StringBuilder mensaje) {
        boolean resultado = false;

        try (Connection connection = DB_Polleria.conectar();
             CallableStatement statement = connection.prepareCall("{CALL sp_EliminarFavorito2(?, ?, ? ,?)}")) {

            statement.setInt(1, idUsuario);
            statement.setInt(2, idPlato);
            statement.registerOutParameter(3, Types.BIT);
            statement.registerOutParameter(4, Types.VARCHAR);

            statement.execute();

            resultado = statement.getBoolean(3);
            mensaje.append(statement.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    public boolean verificarFavorito(int idUsuario, int idPlato) {
        boolean esFavorito = false;
        String sql = "{CALL sp_VerificarFavorito(?, ?, ?)}";

        try (Connection connection = DB_Polleria.conectar();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.setInt(1, idUsuario);
            statement.setInt(2, idPlato);
            statement.registerOutParameter(3, java.sql.Types.BIT);

            statement.execute();

            esFavorito = statement.getBoolean(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return esFavorito;
    }


}
