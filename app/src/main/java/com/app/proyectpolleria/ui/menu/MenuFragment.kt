package com.app.proyectpolleria.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.proyectpolleria.Adapter.CategoriaAdapter
import com.app.proyectpolleria.Adapter.PlatoAdapter
import com.app.proyectpolleria.Entidad.Categoria
import com.app.proyectpolleria.Entidad.Plato
import com.app.proyectpolleria.R
import com.app.proyectpolleria.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.recyclerCategoria)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        categoriaAdapter = CategoriaAdapter(requireActivity(), ArrayList())
        recyclerView.adapter = categoriaAdapter

        observeCategoria(menuViewModel.categoria, categoriaAdapter)


        return root
    }

    private fun observeCategoria(categpriaLiveData: LiveData<List<Categoria>>, categoriaAdapter: CategoriaAdapter) {
        categpriaLiveData.observe(
            viewLifecycleOwner
        ) { categoria ->
            categoria?.let {
                categoriaAdapter.updateCategoria(it)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
