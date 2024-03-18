package com.app.proyectpolleria.ui.inicio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.proyectpolleria.Activity_register
import com.app.proyectpolleria.R
import com.app.proyectpolleria.databinding.FragmentInicioBinding
import com.app.proyectpolleria.ui.menu.MenuFragment
import com.app.proyectpolleria.ui.menu.MenuViewModel

class InicioFragment : Fragment() {

    private lateinit var ordena:Button
    private lateinit var antojo:Button
    private lateinit var promos:Button

    private var _binding: FragmentInicioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inicioViewModel =
            ViewModelProvider(this).get(InicioViewModel::class.java)

        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ordena=binding.ordenaaqui
        antojo=binding.queseteantoja
        promos=binding.promosexclusivas


        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}