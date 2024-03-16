package com.app.proyectpolleria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class Activity_register : AppCompatActivity() {

    private lateinit var btnauntentication: Button
    private lateinit var txtnewcorreo: EditText
    private lateinit var txtnewcontra: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnauntentication= findViewById(R.id.btn_auntentication)
        txtnewcorreo = findViewById(R.id.txt_new_correo)
        txtnewcontra = findViewById(R.id.txt_new_contra)

        setup()

    }

    private fun setup() {
        title = "Autenticación"

        btnauntentication.setOnClickListener {
            if (!txtnewcorreo.text.toString().isEmpty() && !txtnewcontra.text.toString().isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    txtnewcorreo.text.toString(),
                    txtnewcontra.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Éxito al registrar
                        startActivity(Intent(this, Activity_login::class.java))
                    } else {
                        showAlert()
                    }
                }
            }

        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ah producido un error de autentificación de usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

}