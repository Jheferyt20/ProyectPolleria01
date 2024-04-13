package com.app.proyectpolleria.Negocio;

import com.app.proyectpolleria.Datos.PlatoDatos;
import com.app.proyectpolleria.Entidad.Plato;

import java.util.List;

public class PlatoNegocio {

    PlatoDatos datoPlatos = new PlatoDatos();

    public List<Plato> ListarPlato(int idPlato) {
        return datoPlatos.ListarPlato(idPlato);
    }

}
