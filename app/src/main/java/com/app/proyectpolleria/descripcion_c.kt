package com.app.proyectpolleria

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.proyectpolleria.Entidad.Plato
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson

class descripcion_c : AppCompatActivity() {

    private lateinit var btnBack: ImageButton

    private lateinit var imgFavoritoSin : ImageView
    private lateinit var imgFavoritoPintado : ImageView
    private  lateinit var  txtnombre :TextView
    private  lateinit var  txtprecio :TextView
    private lateinit var imgPlato :ImageView
    private lateinit var txtdescripcion : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_c)
        imgFavoritoPintado = findViewById(R.id.favorito_pintado)
        txtnombre = findViewById(R.id.txtNombre)
        txtdescripcion = findViewById(R.id.txtDescripcion)
        txtprecio = findViewById(R.id.txtPrecio)
        imgPlato = findViewById(R.id.imgPlato)
        imgFavoritoSin = findViewById(R.id.favorito_sin)
        imgFavoritoPintado.visibility = View.GONE

        val intent = intent
        val platoJson = intent.getStringExtra("plato")
        val plato: Plato = Gson().fromJson(platoJson, Plato::class.java)
        txtnombre.setText(plato.nombre)
        txtdescripcion.setText(plato.descripcion)
        txtprecio.setText("S/ " + plato.precio.toString())
        val url_imagen = plato.imgPlato
        VerImagen(url_imagen)


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

    private fun VerImagen(urlImagen: String) {
        if (urlImagen.isNotBlank()) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img_4)
            Glide.with(this)
                .load(urlImagen)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img_4)
                .into(imgPlato)
        }
    }




    private fun goBack() {
        // Finaliza la actividad actual
        finish()
    }

}