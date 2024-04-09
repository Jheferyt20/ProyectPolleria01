package com.app.proyectpolleria.ui.plato

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.proyectpolleria.Adapter.PlatoAdapter
import com.app.proyectpolleria.Entidad.Plato
import com.app.proyectpolleria.R
import com.app.proyectpolleria.databinding.FragmentPlatosBinding
import com.app.proyectpolleria.ui.menu.MenuViewModel

class PlatosFragment : Fragment() {

    private var _binding: FragmentPlatosBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlatosViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var platoAdapter: PlatoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemId = arguments?.getInt("itemId", 0) ?: 0
        viewModel = ViewModelProvider(this, PlatosViewModelFactory(itemId)).get(PlatosViewModel::class.java)

        _binding = FragmentPlatosBinding.inflate(inflater, container, false)
        val root: View = binding.root


        recyclerView = root.findViewById(R.id.recyclerPlato)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        platoAdapter = PlatoAdapter(requireActivity(), ArrayList())
        recyclerView.adapter = platoAdapter

        observePlatos(viewModel.plato, platoAdapter)

        return  root

    }

    private fun observePlatos(platoLiveData: LiveData<List<Plato>>, platoAdapter: PlatoAdapter) {
        platoLiveData.observe(
            viewLifecycleOwner
        ) { plato ->
            plato?.let {
                platoAdapter.updatePlato(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}