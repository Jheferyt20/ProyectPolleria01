package com.app.proyectpolleria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class descripcion_c : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_c)

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