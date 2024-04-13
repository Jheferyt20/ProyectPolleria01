package com.app.proyectpolleria.Negocio;

import com.app.proyectpolleria.Datos.CarritoDatos;
import com.app.proyectpolleria.Datos.UsuarioDatos;

public class CarritoNegocio {

    private CarritoDatos datosCarrito = new CarritoDatos();

    public boolean buscarCarrito(int idUsuario, int idPlato){
        return  datosCarrito.buscarCarrito(idUsuario,idPlato);
    }

    public Boolean OperacionCarrito(int IdCliente, int IdPlato, Boolean Sumar,int cantidad, StringBuilder mensaje){
        return  datosCarrito.OperacionCarrito(IdCliente ,IdPlato , Sumar ,cantidad ,mensaje);
    }

    public boolean eliminarCarrito(int idCarrito) {
        return datosCarrito.eliminarCarrito(idCarrito);
    }

}
