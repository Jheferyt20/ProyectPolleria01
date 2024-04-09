package com.app.proyectpolleria

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.app.proyectpolleria.Entidad.Usuario
import com.app.proyectpolleria.Negocio.UsuarioNegocio
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class DatosActivity : AppCompatActivity() {

    private lateinit var txtUsuario : EditText
    private lateinit var txtnombre : EditText
    private lateinit var txtapellido : EditText
    private lateinit var txtcorreo : EditText
    private lateinit var txttelefono : EditText
    private lateinit var txtdireccion : EditText
    private lateinit var  imgFoto : ImageView
    private lateinit var  btnEditar : Button
    private lateinit var  btnPerfil : Button
    private val clNegocio = UsuarioNegocio()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var idUsuario:kotlin.Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_datos)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Datos Personales")
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtnombre = findViewById(R.id.txtnombres)
        txtapellido = findViewById(R.id.txtapellidos)
        txtUsuario = findViewById(R.id.txtusuario)
        txttelefono = findViewById(R.id.txtTelefono)
        txtcorreo = findViewById(R.id.txtcorreo)
        txtdireccion = findViewById(R.id.txtdireccion)
        imgFoto = findViewById(R.id.foto)
        btnEditar = findViewById(R.id.btneditar)
        btnPerfil = findViewById(R.id.btnPerfil)
        imgFoto.setScaleType(ImageView.ScaleType.CENTER_CROP)


        val preferences: SharedPreferences = this.getSharedPreferences("datos_usuario", MODE_PRIVATE)
        idUsuario = preferences.getInt("USUARIO", 0)
        DevolverDatos(idUsuario)

        btnEditar.setOnClickListener {
            EidtarUsuario(idUsuario)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent);
        }

    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val uri: Uri? = result.data?.data
            if (uri != null) {
                // Coloca aquí el código para subir la imagen a Firebase Storage y cargarla con Glide
                val nombreArchivo = UUID.randomUUID().toString() + ".jpg"
                val imagenRef = storage.getReference().child("images/$nombreArchivo")
                val uploadTask = imagenRef.putFile(uri)
                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        imagenRef.downloadUrl.addOnCompleteListener { downloadTask ->
                            if (downloadTask.isSuccessful) {
                                val url = downloadTask.result.toString()
                                GuardarImagen(url)
                            } else {
                                Log.e("Error", "Error al obtener la URL de descarga", downloadTask.exception)
                            }
                        }
                    } else {
                        Log.e("Error", "Error al subir la imagen", task.exception)
                    }
                }
            }
        }
    }


    private fun DevolverDatos(idUsuario: Int) {
        val usuarios = clNegocio.recibirDatos(idUsuario)
        if (usuarios.isNotEmpty()) {
            val usuario = usuarios[0]

            val User = usuario.usuario
            val nombre = usuario.nombre
            val apellido = usuario.apellido
            val  direccion = usuario.direccion
            val correo = usuario.correo
            val telefono = usuario.telefono
            val urlImagen = usuario.img


            txtUsuario.setText(User)
            txtnombre.setText(nombre)
            txtapellido.setText(apellido)
            txtdireccion.setText(direccion)
            txtcorreo.setText(correo)
            txttelefono.setText(telefono)

            if(urlImagen != null){
                VerImagen(urlImagen)
            }
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun GuardarImagen( urlImagen: String){
        val exitoso = clNegocio.GuardarImagen(idUsuario , urlImagen)
        if(exitoso == true){
            VerImagen(urlImagen)
        }else{
            Toast.makeText(this, "Hubo error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }
    }


    private fun VerImagen(urlImagen: String) {
        if (urlImagen.isNotBlank()) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img)
            Glide.with(this)
                .load(urlImagen)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img)
                .into(imgFoto)
        }
    }



    private fun EidtarUsuario( id_Usuario  : Int){
        val usuario = Usuario().apply {
            id = id_Usuario
            usuario = txtUsuario.text.toString()
            nombre = txtnombre.text.toString()
            apellido = txtapellido.text.toString()
            correo = txtcorreo.text.toString()
            telefono = txttelefono.text.toString()
            direccion = txtdireccion.text.toString()
        }

        val mensaje = StringBuilder()

        val resultado = clNegocio.EditarUsuario(usuario, mensaje)

        // Verificar el resultado y mostrar un mensaje al usuario
        if (resultado != 0) {
            Toast.makeText(this, "Actulizo sus datos exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            // Verificar si hay un mensaje de error y mostrarlo
            if (mensaje.isNotEmpty()) {
                Toast.makeText(this, " $mensaje", Toast.LENGTH_SHORT).show()
            } else {
                // Si no hay mensaje de error, mostrar un mensaje genérico
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // o realiza la acción que desees al regresar
            return true
        }
        return super.onOptionsItemSelected(item)
    }




}