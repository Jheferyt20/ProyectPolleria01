package com.app.proyectpolleria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.proyectpolleria.Entidad.Usuario
import com.app.proyectpolleria.Negocio.UsuarioNegocio

class Activity_register : AppCompatActivity() {

    private lateinit var btnregistrar: Button
    private lateinit var btnatras: ImageButton
    private lateinit var txtusuario : EditText
    private lateinit var txtnombre : EditText
    private lateinit var txtapellido : EditText
    private lateinit var txtcorreo : EditText
    private lateinit var txtclave : EditText
    private lateinit var txttelefono : EditText
    private val clNegocio = UsuarioNegocio()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnregistrar = findViewById(R.id.btn_registrar)
        txtusuario = findViewById(R.id.txtusuario)
        txtnombre = findViewById(R.id.txtnombres)
        txtapellido = findViewById(R.id.txtapellidos)
        txtcorreo = findViewById(R.id.txtcorreo)
        txtclave = findViewById(R.id.txtclave)
        txttelefono = findViewById(R.id.txtTelefono)
        btnatras = findViewById(R.id.btn_back)


        btnatras.setOnClickListener{
            startActivity(Intent(this, Activity_login::class.java))
        }

        btnregistrar.setOnClickListener {
            // Crear un objeto Usuario y establecer sus campos con los datos del formulario
            val usuario = Usuario().apply {
                usuario = txtusuario.text.toString()
                nombre = txtnombre.text.toString()
                apellido = txtapellido.text.toString()
                correo = txtcorreo.text.toString()
                clave = txtclave.text.toString()
                telefono = txttelefono.text.toString()
            }

            val mensaje = StringBuilder()

            val resultado = clNegocio.ResgistrarUsuario(usuario, mensaje)

            if (resultado != 0) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar si hay un mensaje de error y mostrarlo
                if (mensaje.isNotEmpty()) {
                    Toast.makeText(this, "Error al registrar: $mensaje", Toast.LENGTH_SHORT).show()
                } else {
                    // Si no hay mensaje de error, mostrar un mensaje genérico
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

        private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Felicidades")
        builder.setMessage("Te has registrado exitosamente!!")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

}