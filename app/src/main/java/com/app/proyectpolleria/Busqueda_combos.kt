package com.app.proyectpolleria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Busqueda_combos : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnPromocion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda_combos)

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