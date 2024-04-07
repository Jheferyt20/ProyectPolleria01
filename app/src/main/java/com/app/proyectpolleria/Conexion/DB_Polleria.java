package com.app.proyectpolleria.Conexion;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Polleria {

    public static Connection conectar() {
        Connection cnn =null;
        try {
            StrictMode.ThreadPolicy politica= new  StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn= DriverManager.getConnection("jdbc:jtds:sqlserver://Polleria.mssql.somee.com;user=PauloYas_SQLLogin_1;password=wuqyiy3npx;databaseName=Polleria");

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return cnn;
    }

}