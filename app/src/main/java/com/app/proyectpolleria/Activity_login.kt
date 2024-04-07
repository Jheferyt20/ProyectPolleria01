package com.app.proyectpolleria

import android.content.Context
import android.content.Intent
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
            val exitoso = clNegocio.LoguearUsuario(correo, clave , mensaje)

            if (exitoso) {
                startActivity(Intent(this, Home::class.java))
            } else {

                // Verificar si hay un mensaje de error y mostrarlo
                if (mensaje.isNotEmpty()) {
                    Toast.makeText(this, "Error al iniciar sesión: $mensaje", Toast.LENGTH_SHORT).show()
                } else {
                    // Si no hay mensaje de error, mostrar un mensaje genérico
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

    private fun showHome() {
        val homeIntent = Intent(this, Home::class.java)
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
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (task.result?.additionalUserInfo?.isNewUser == true) {
                        val photoUri = user?.photoUrl
                        val usuario = Usuario().apply {
                            nombre = user?.displayName
                            apellido = account?.familyName
                            correo = user?.email
                            img = photoUri?.toString() ?: ""
                        }

                        registrarUsuarioConGoogle(usuario)
                    } else {
                        // El usuario ya existe, mostrar mensaje de error
                        showHome()
                    }
                } else {
                    // Mostrar mensaje de error en caso de fallo en la autenticación
                    Toast.makeText(this, "Hubo problemas", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun registrarUsuarioConGoogle(usuario: Usuario) {
        val mensaje = StringBuilder()
        val resultado = clNegocio.ResgistrarUsuarioGoogle(usuario, mensaje)

        if (resultado != 0) {
            showHome()
        } else {
            if (mensaje.isNotEmpty()) {
                Toast.makeText(this, "Error al registrar usuario: ${mensaje.toString()}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }




}
