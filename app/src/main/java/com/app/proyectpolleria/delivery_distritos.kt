package com.app.proyectpolleria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class delivery_distritos : AppCompatActivity() {
    private lateinit var btnBack: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_distritos)

        btnBack = findViewById(R.id.btnAtras)

        btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        // Finaliza la actividad actual
        finish()
    }
}