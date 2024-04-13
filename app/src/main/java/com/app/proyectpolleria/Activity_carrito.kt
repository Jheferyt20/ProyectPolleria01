package com.app.proyectpolleria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.proyectpolleria.Adapter.CarritoAdapter
import com.app.proyectpolleria.Entidad.Carrito
import com.app.proyectpolleria.Repositorio.CarritoRepositorio

class Activity_carrito: AppCompatActivity() {
    private lateinit var btnBack: Button
    private  lateinit var  recyclerView: RecyclerView
    private lateinit var text1 : TextView
    private lateinit var text2 : TextView
    private lateinit var imgLogo : ImageView
    private lateinit var btnFindeCompra : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carritouno)
        recyclerView = findViewById(R.id.recyclerCarrito)
        text1 = findViewById(R.id.txtTexto1)
        text2 = findViewById(R.id.txtTexto2)
        imgLogo = findViewById(R.id.imglogo)
        btnFindeCompra = findViewById(R.id.btnPagar)
        btnBack = findViewById(R.id.btn_back)

         val carritoRepositorio = CarritoRepositorio()
        val preferences = this.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        val idUsuario = preferences.getInt("USUARIO", 0)
        recyclerView.visibility = View.GONE

        carritoRepositorio.getCarritoFromDatabase(idUsuario, object : CarritoRepositorio.ListaCarritoCall {
            override fun onSuccess(carritos: List<Carrito>) {

                if(carritos !=null){
                    recyclerView.visibility = View.VISIBLE
                    text1.visibility = View.GONE
                    text2.visibility = View.GONE
                    imgLogo.visibility = View.GONE
                    val adaptercarrito = CarritoAdapter(this@Activity_carrito , carritos)
                    recyclerView.layoutManager= GridLayoutManager(this@Activity_carrito , 1)
                    recyclerView.adapter = adaptercarrito
                }
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@Activity_carrito, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })


        btnFindeCompra.setOnClickListener {
            val intent = Intent(this, FinComprasActivity::class.java)
            startActivity(intent)
        }


        btnBack.setOnClickListener {
            goBack()
        }
    }



    private fun goBack() {
        finish()
    }
}