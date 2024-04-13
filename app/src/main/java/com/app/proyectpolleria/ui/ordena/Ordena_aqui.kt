package com.app.proyectpolleria.ui.ordena

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.app.proyectpolleria.Google_maps_api
import com.app.proyectpolleria.R
import com.app.proyectpolleria.delivery_distritos

/**
 * A simple [Fragment] subclass.
 */
class Ordena_aqui : Fragment() {

    private lateinit var btnDelivery: LinearLayout
    private lateinit var btnRecojoTienda: LinearLayout

    companion object {
        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Ordena_aqui().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ordena_aqui, container, false)

        btnDelivery = view.findViewById(R.id.btnDelivery)
        btnRecojoTienda = view.findViewById(R.id.btnRecojoTienda)

        btnRecojoTienda.setOnClickListener {
            val intent = Intent(context, Google_maps_api::class.java)
            startActivity(intent)
        }

        btnDelivery.setOnClickListener {
            val intent = Intent(context, delivery_distritos::class.java)
            startActivity(intent)
        }

        return view
    }
}
