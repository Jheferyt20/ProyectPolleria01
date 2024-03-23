package com.app.proyectpolleria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class politica_empresa : AppCompatActivity() {

    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_politica_empresa)

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
