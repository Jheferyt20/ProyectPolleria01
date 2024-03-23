package com.app.proyectpolleria

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity_carrito: AppCompatActivity() {
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carritouno)

        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        // Finaliza la actividad actual
        finish()
    }
}