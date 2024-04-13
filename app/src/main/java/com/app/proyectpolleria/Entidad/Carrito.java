package com.app.proyectpolleria.Entidad;

public class Carrito {

    private int id ;
    private int id_Usuario;
    private int id_Plato;
    private int Cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public int getId_Plato() {
        return id_Plato;
    }

    public void setId_Plato(int id_Plato) {
        this.id_Plato = id_Plato;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
}
