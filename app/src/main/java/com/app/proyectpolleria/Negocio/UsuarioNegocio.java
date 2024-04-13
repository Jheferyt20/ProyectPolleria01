package com.app.proyectpolleria.Negocio;

import com.app.proyectpolleria.Datos.UsuarioDatos;
import com.app.proyectpolleria.Entidad.Usuario;

import java.sql.ResultSet;
import java.util.List;

public class UsuarioNegocio {


    private UsuarioDatos datosUser = new UsuarioDatos();

    public int ResgistrarUsuario(Usuario obj, StringBuilder mensaje) {
        if (mensaje == null) {
            mensaje = new StringBuilder();
        }

        if (obj.getUsuario() == null || obj.getUsuario().trim().isEmpty()) {
            mensaje.append("El usuario no puede ser vacío");
        }else if (obj.getNombre() == null || obj.getNombre().trim().isEmpty()) {
            mensaje.append("El nombre no puede ser vacío");
        } else if (obj.getApellido() == null || obj.getApellido().trim().isEmpty()) {
            mensaje.append("El apellido no puede ser vacío");
        } else if (obj.getCorreo() == null || obj.getCorreo().trim().isEmpty()) {
            mensaje.append("El correo no puede ser vacío");
        } else if (obj.getClave() == null || obj.getClave().trim().isEmpty()) {
            mensaje.append("La clave no puede ser vacía");
        } else if (obj.getTelefono() == null || obj.getTelefono().trim().isEmpty()) {
            mensaje.append("El número de teléfono no puede ser vacío");
        }

        if (mensaje.length() > 0) {
            return 0; // Indica que hay errores, pero no llama a RegistrarUsuario
        } else {
            // Aquí se convierte StringBuilder a String antes de llamar a RegistrarUsuario
            return datosUser.RegistrarUsuario(obj, mensaje);
        }
    }

    public int ResgistrarUsuarioGoogle(Usuario obj, StringBuilder mensaje) {
        return  datosUser.RegistrarUsuarioGoogle(obj, mensaje);
    }


    public Boolean LoguearUsuario(String correo , String clave, StringBuilder mensaje){
        if (mensaje == null) {
            mensaje = new StringBuilder();
        }

        if (correo == null || correo.trim().isEmpty()) {
            mensaje.append("Complete su correo");
        } else if (clave == null ||clave.trim().isEmpty()) {
            mensaje.append("Complete su clave");
        }

        Boolean exito = datosUser.Login(correo , clave);
        if(exito ==  true){
            return  exito;
        }else{
            mensaje.append("Verifica tu correo o tu clave");
            return  exito;
        }
    }


    public int consultaUsuario(String correo){
        return datosUser.consultarpersonal(correo);
    }

    public List<Usuario> recibirDatos(int idUsuario){
        return  datosUser.recibirDatos(idUsuario);
    }

    public int EditarUsuario(Usuario obj, StringBuilder mensaje) {
        if (obj.getNombre() == null || obj.getNombre().trim().isEmpty()) {
            mensaje.append("El nombre no puede ser vacío");
        } else if (obj.getApellido() == null || obj.getApellido().trim().isEmpty()) {
            mensaje.append("El apellido no puede ser vacío");
        } else if (obj.getCorreo() == null || obj.getCorreo().trim().isEmpty()) {
            mensaje.append("El correo no puede ser vacío");
        }

        if (mensaje.length() > 0) {
            return 0; // Indica que hay errores, pero no llama a RegistrarUsuario
        } else {
            return datosUser.editarUsuario(obj, mensaje);
        }

    }

    public  boolean GuardarImagen(int id , String url){
        return  datosUser.GuardarImagen(id, url);
    }




}
