package com.app.proyectpolleria

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.app.proyectpolleria.Entidad.Usuario
import com.app.proyectpolleria.Negocio.UsuarioNegocio
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Activity_login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnregister: Button
    private lateinit var btngoogle: Button
    private lateinit var txtcorreo: EditText
    private lateinit var txtcontra: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var iniciogoogle: GoogleSignInClient
    private val clNegocio = UsuarioNegocio()
    private  val correoelectronico = String.toString()

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        btnLogin = findViewById(R.id.btn_login)
        btnregister = findViewById(R.id.btn_register)
        txtcorreo = findViewById(R.id.txt_correo)
        txtcontra = findViewById(R.id.txt_contra)
        btngoogle = findViewById(R.id.btnGoogle)

        crearSolicitud()

        btnLogin.setOnClickListener {
            val correo = txtcorreo.text.toString().trim()
            val clave = txtcontra.text.toString().trim()
            val mensaje = StringBuilder()
            val exitoso = clNegocio.LoguearUsuario(correo, clave, mensaje)

            if (exitoso) {
                // Consultar el ID del usuario después de iniciar sesión correctamente
                val idUsuario = clNegocio.consultaUsuario(correo)

                if (idUsuario != 0) {
                    // Si se encuentra el ID del usuario, guardarlo en SharedPreferences y abrir la pantalla principal
                    val preferences: SharedPreferences = this.getSharedPreferences("datos_usuario", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putInt("USUARIO", idUsuario)
                    editor.apply()
                    guardarSesionActiva()
                    startActivity(Intent(this, Home::class.java))
                } else {
                    Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (mensaje.isNotEmpty()) {
                    Toast.makeText(this, "Error al iniciar sesión: $mensaje", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btngoogle.setOnClickListener { Iniciogoogle() }

        btnregister.setOnClickListener {
            startActivity(Intent(this, Activity_register::class.java))
        }
    }



    private fun guardarEstadoInicioSesion(email: String) {
        val preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al iniciar sesión")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(correo: String) {
        val homeIntent = Intent(this, Home::class.java)
        val idUsuario = clNegocio.consultaUsuario(correo) // Asegúrate de que esta función devuelva un entero
        val preferences: SharedPreferences = this.getSharedPreferences("datos_usuario", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("USUARIO", idUsuario)
        editor.apply()
        guardarSesionActiva()
        startActivity(homeIntent)
    }



    private fun Iniciogoogle() {
        val signInIntent = iniciogoogle.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun crearSolicitud() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        iniciogoogle = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                autenticacionFirebase(account)
            } catch (ex: ApiException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun autenticacionFirebase(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                val user = auth.currentUser
                val correoelectrinico = user?.email ?: ""
                if (task.isSuccessful) {
                    if (task.result?.additionalUserInfo?.isNewUser == true) {
                        val photoUri = user?.photoUrl
                        val usuario = Usuario().apply {
                            nombre = user?.displayName
                            apellido = account?.familyName
                            correo = correoelectrinico
                            img = photoUri?.toString() ?: ""
                        }
                        registrarUsuarioConGoogle(usuario)
                    } else {
                        showHome(correoelectrinico)
                    }
                } else {
                    Toast.makeText(this, "Hubo problemas", Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun registrarUsuarioConGoogle(usuario: Usuario) {
        val mensaje = StringBuilder()
        val resultado = clNegocio.ResgistrarUsuarioGoogle(usuario, mensaje)

        if (resultado != 0) {
            showHome(usuario.correo)
        } else {
            if (mensaje.isNotEmpty()) {
                Toast.makeText(this, "Error al registrar usuario: ${mensaje.toString()}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarSesionActiva() {
        val preferences = getSharedPreferences("sesion", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("sesionActiva", true)
        editor.apply()
    }






}
