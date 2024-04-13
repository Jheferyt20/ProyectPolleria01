package com.app.proyectpolleria

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.proyectpolleria.Entidad.Plato
import com.app.proyectpolleria.Negocio.CarritoNegocio
import com.app.proyectpolleria.Negocio.FavoritoNegocio
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
    private  lateinit var  btnAgregar : Button
    private val handler = Handler()
    private var isClickPending = false
    private val DELAY_MILLISECONDS = 1000
    private val mensaje: StringBuilder = StringBuilder()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_c)
        imgFavoritoPintado = findViewById(R.id.favorito_pintado)
        txtnombre = findViewById(R.id.txtNombre)
        txtdescripcion = findViewById(R.id.txtDescripcion)
        txtprecio = findViewById(R.id.txtPrecio)
        imgPlato = findViewById(R.id.imgPlato)
        btnAgregar = findViewById(R.id.agregar_carrito)
        btnBack = findViewById(R.id.btn_back)
        imgFavoritoSin = findViewById(R.id.favorito_sin)
        imgFavoritoPintado.visibility = View.GONE

        val clNegocio = CarritoNegocio()
        val clFavorito = FavoritoNegocio()

        val intent = intent
        val platoJson = intent.getStringExtra("plato")
        val plato: Plato = Gson().fromJson(platoJson, Plato::class.java)
        txtnombre.setText(plato.nombre)
        txtdescripcion.setText(plato.descripcion)
        txtprecio.setText("S/ " + plato.precio.toString())
        val url_imagen = plato.imgPlato
        VerImagen(url_imagen)
        val preferences = this.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        val idUsuario = preferences.getInt("USUARIO", 0)
        val resultado = clFavorito.verificarFavorito(idUsuario,plato.id)
        val resultado2 = clNegocio.buscarCarrito(idUsuario , plato.id)
        if(resultado2){
            btnAgregar.visibility = View.GONE
        }

        if (resultado){
            imgFavoritoSin.visibility = View.GONE
            imgFavoritoPintado.visibility = View.VISIBLE
        }

        imgFavoritoSin.setOnClickListener{
            if (imgFavoritoSin.visibility == View.VISIBLE) {
                imgFavoritoPintado.visibility = View.VISIBLE
                imgFavoritoSin.visibility = View.GONE
                animateLike(imgFavoritoPintado)
                if (isClickPending) {
                    handler.removeCallbacksAndMessages(null);
                }
                handler.postDelayed({
                    val result = clFavorito.registrarFavorito(idUsuario,plato.id,mensaje)
                    if(!result){
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    }
                    isClickPending = false
                }, DELAY_MILLISECONDS.toLong())
                isClickPending = true;

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
                animateLike(imgFavoritoSin)

                if (isClickPending) {
                    handler.removeCallbacksAndMessages(null);
                }
                handler.postDelayed({
                    val result = clFavorito.eliminarFavorito2(idUsuario,plato.id,mensaje)
                    if(!result){
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    }
                    isClickPending = false
                }, DELAY_MILLISECONDS.toLong())
                isClickPending = true;

            } else {
                imgFavoritoPintado.visibility = View.VISIBLE
                imgFavoritoSin.visibility = View.GONE
                animateLike(imgFavoritoPintado) // Pasar imgFavoritoPintado como argumento
            }
        }

        btnBack.setOnClickListener {
            goBack()
        }


        btnAgregar.setOnClickListener {
            val mensaje = StringBuilder()
            val resultado = clNegocio.OperacionCarrito(idUsuario, plato.id,true,1,mensaje)
            if (resultado == true) {
                Toast.makeText(this, "Se añadio al carrito", Toast.LENGTH_SHORT).show()
            } else {

                if (mensaje.isNotEmpty()) {
                    Toast.makeText(this, "Error al registrar: $mensaje", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }

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