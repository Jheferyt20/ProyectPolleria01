package com.app.proyectpolleria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Activity_login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnregister: Button
    private lateinit var txtcorreo: EditText
    private lateinit var txtcontra: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin= findViewById(R.id.btn_login)
        btnregister= findViewById(R.id.btn_register)
        txtcorreo = findViewById(R.id.txt_correo)
        txtcontra = findViewById(R.id.txt_contra)





        singIn()

        btnregister.setOnClickListener {
            startActivity(Intent(this, Activity_register::class.java))
        }

    }

    private fun singIn(){
        title = "Sing In"

        btnLogin.setOnClickListener {
            if (!txtcorreo.text.toString().isEmpty() && !txtcontra.text.toString().isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    txtcorreo.text.toString(),
                    txtcontra.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Éxito al acceder
                        //val email = task.result?.user?.email ?: ""
                        val preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE)
                        val email = preferences.getString("email", null)
                        if (email != null) {
                            // El usuario ya ha iniciado sesión, redirigirlo a la pantalla principal
                            showHome(email)
                        }
                        guardarEstadoInicioSesion(task.result?.user?.email ?: "")
                    } else {
                        showAlert()
                    }
                }
            }

        }
    }

    private fun guardarEstadoInicioSesion(email: String) {
        val preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ah producido un error de logeo de usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun showHome(email:String ){
        val homeIntent= Intent(this,Home::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }
}