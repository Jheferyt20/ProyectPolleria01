package com.app.proyectpolleria

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class descripcion_c : AppCompatActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var imgFavoritoSin : ImageView
    private lateinit var imgFavoritoPintado : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_c)
        imgFavoritoPintado = findViewById(R.id.favorito_pintado)
        imgFavoritoSin = findViewById(R.id.favorito_sin)

        imgFavoritoPintado.visibility = View.GONE

        imgFavoritoSin.setOnClickListener{
            if (imgFavoritoSin.visibility == View.VISIBLE) {
                imgFavoritoPintado.visibility = View.VISIBLE
                imgFavoritoSin.visibility = View.GONE
                animateLike(imgFavoritoPintado) // Pasar imgFavoritoPintado como argumento
            } else {
                imgFavoritoSin.visibility = View.VISIBLE
                imgFavoritoPintado.visibility = View.GONE
                animateLike(imgFavoritoSin) // Pasar imgFavoritoSin como argumento
            }
        }

        imgFavoritoPintado.setOnClickListener{
            if (imgFavoritoPintado.visibility == View.VISIBLE) {
                imgFavoritoSin.visibility = View.VISIBLE
                imgFavoritoPintado.visibility = View.GONE
                animateLike(imgFavoritoSin) // Pasar imgFavoritoSin como argumento
            } else {
                imgFavoritoPintado.visibility = View.VISIBLE
                imgFavoritoSin.visibility = View.GONE
                animateLike(imgFavoritoPintado) // Pasar imgFavoritoPintado como argumento
            }
        }


        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            goBack()
        }


    }

    private fun animateLike(imageView: ImageView) {

        imageView.clearAnimation()

        val scaleAnimation = ScaleAnimation(
            1f, 1.2f,
            1f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 200
        scaleAnimation.repeatMode = Animation.REVERSE
        scaleAnimation.repeatCount = 1

        imageView.startAnimation(scaleAnimation)
    }




    private fun goBack() {
        // Finaliza la actividad actual
        finish()
    }

}