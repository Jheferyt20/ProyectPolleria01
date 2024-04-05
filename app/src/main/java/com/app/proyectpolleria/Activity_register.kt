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