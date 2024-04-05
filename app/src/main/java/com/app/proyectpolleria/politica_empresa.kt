package com.app.proyectpolleria

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class politica_empresa : AppCompatActivity() {

    private lateinit var btnBack: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_politica_empresa)

        btnBack = findViewById(R.id.btnAtras)

        btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        // Finaliza la actividad actual
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
