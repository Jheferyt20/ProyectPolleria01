package com.app.proyectpolleria.ui.micuenta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.proyectpolleria.databinding.FragmentMicuentaBinding

class MicuentaFragment : Fragment() {

    private var _binding: FragmentMicuentaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val micuentaViewModel =
            ViewModelProvider(this).get(MicuentaViewModel::class.java)

        _binding = FragmentMicuentaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMicuenta
        micuentaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
