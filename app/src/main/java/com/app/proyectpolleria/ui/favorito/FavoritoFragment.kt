package com.app.proyectpolleria.ui.favorito

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.proyectpolleria.Adapter.CategoriaAdapter
import com.app.proyectpolleria.Adapter.FavoritoAdapter
import com.app.proyectpolleria.Adapter.FavoritoAdapter.FavoritoViewHolder
import com.app.proyectpolleria.Entidad.Categoria
import com.app.proyectpolleria.Entidad.Favorito
import com.app.proyectpolleria.databinding.FragmentFavoritoBinding
import com.app.proyectpolleria.R
import com.app.proyectpolleria.ui.menu.MenuViewModel

class FavoritoFragment : Fragment() {

    private var _binding: FragmentFavoritoBinding? = null
    private  lateinit var  cardview :RecyclerView
    private lateinit var favoritoView : FavoritoViewModel
    private lateinit var favoritoAdapter: FavoritoAdapter
    private lateinit var  txtTexto : TextView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModelFactory = FavoritoViewModelFactory(requireContext())
        favoritoView = ViewModelProvider(this, viewModelFactory).get(FavoritoViewModel::class.java)

        _binding = FragmentFavoritoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cardview = root.findViewById(R.id.recyclerFavoritos )
        txtTexto = root.findViewById(R.id.textF)
        cardview.visibility = View.GONE
        cardview.layoutManager = GridLayoutManager(requireContext(), 2)
        favoritoAdapter = FavoritoAdapter(requireActivity(), ArrayList())
        cardview.adapter = favoritoAdapter

        observeFavorito(favoritoView.favorito , favoritoAdapter)

        return root
    }

    override fun onResume() {
        super.onResume()
        observeFavorito(favoritoView.favorito , favoritoAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(FavoritoViewModel::class.java)
        val preferences = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        val idUsuario = preferences.getInt("USUARIO", 0)
        viewModel.inicializar(idUsuario)
    }


    private fun observeFavorito(favoritoLiveData: LiveData<List<Favorito>>, favoritoAdapter: FavoritoAdapter) {
        favoritoLiveData.observe(
            viewLifecycleOwner
        ) { favorito ->
            favorito?.let {
                cardview.visibility= View.VISIBLE
                txtTexto.visibility = View.GONE
                favoritoAdapter.updateFavorito(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}