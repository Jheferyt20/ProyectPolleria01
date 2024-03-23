package com.app.proyectpolleria.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.proyectpolleria.Activity_carrito
import com.app.proyectpolleria.Busqueda_combos
import com.app.proyectpolleria.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var btnCombos:Button

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        btnCombos=binding.combos

        btnCombos.setOnClickListener {
            val intent = Intent(context, Busqueda_combos::class.java)
            startActivity(intent)
        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}