package com.app.proyectpolleria.Entidad;

import java.math.BigDecimal;

public class Plato {

    private int id ;
    private String nombre;
    private  String descripcion;
    private BigDecimal precio;
    private  int idCategoria;
    private  String imgPlato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getIdCategoria(int categoriaId) {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImgPlato() {
        return imgPlato;
    }

    public void setImgPlato(String imgPlato) {
        this.imgPlato = imgPlato;
    }
}
