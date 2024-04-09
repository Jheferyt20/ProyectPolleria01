package com.app.proyectpolleria.ui.favorito

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.proyectpolleria.databinding.FragmentFavoritoBinding
import com.app.proyectpolleria.R

class FavoritoFragment : Fragment() {

    private var _binding: FragmentFavoritoBinding? = null
    private  lateinit var  cardview :RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritoViewModel =
            ViewModelProvider(this).get(FavoritoViewModel::class.java)

        _binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cardview = root.findViewById(R.id.recyclerFavoritos )
        cardview.visibility = View.GONE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}