package com.app.proyectpolleria.ui.micuenta

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.proyectpolleria.Activity_carrito
import com.app.proyectpolleria.Activity_login
import com.app.proyectpolleria.ComprasActivity
import com.app.proyectpolleria.DatosActivity
import com.app.proyectpolleria.R
import com.app.proyectpolleria.databinding.FragmentMicuentaBinding
import com.app.proyectpolleria.politica_empresa
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class MicuentaFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var cardview: CardView
    private lateinit var btnCerrarSesion: LinearLayout
    private lateinit var btnCompras: LinearLayout
    private lateinit var btnPolitica: LinearLayout
    private lateinit var btnEditar: LinearLayout
    private lateinit var vusuario: EditText
    private lateinit var vnombre: EditText
    private lateinit var vapellido: EditText
    private lateinit var vtelefono: EditText
    private lateinit var vdireccion: EditText
    private lateinit var usuario: TextView
    private lateinit var nombre: TextView
    private lateinit var apellido: TextView
    private lateinit var telefono: TextView
    private lateinit var correo: TextView
    private lateinit var direccion: TextView
    private  lateinit var  galery : CardView
    private lateinit var  imagenfoto : ImageView

    private var _binding: FragmentMicuentaBinding? = null
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

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

        galery = binding.imgboton;
        imagenfoto = root.findViewById<ImageView>(R.id.imageView)
        btnEditar = root.findViewById(R.id.linedit)
        btnCompras = root.findViewById(R.id.linecompras)
        btnPolitica = root.findViewById(R.id.linepolitica)
        btnCerrarSesion = root.findViewById(R.id.linesalir)
        imagenfoto.setScaleType(ImageView.ScaleType.CENTER_CROP)

        galery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent);
        }

        btnEditar.setOnClickListener{
            val  intent = Intent(context , DatosActivity::class.java)
            startActivity(intent)
        }

        btnCompras.setOnClickListener{
            val  intent = Intent(context , ComprasActivity::class.java)
            startActivity(intent)
        }

        btnPolitica.setOnClickListener{
            val intent = Intent(context , politica_empresa::class.java)
            startActivity(intent)
        }


        btnCerrarSesion.setOnClickListener{
            val intent = Intent(context, Activity_login::class.java)
            startActivity(intent)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val uri: Uri? = result.data?.data
            if (uri != null) {
                // Coloca aquí el código para subir la imagen a Firebase Storage y cargarla con Glide
                val nombreArchivo = UUID.randomUUID().toString() + ".jpg"
                val imagenRef = storage.getReference().child("images/$nombreArchivo")
                val uploadTask = imagenRef.putFile(uri)
                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        imagenRef.downloadUrl.addOnCompleteListener { downloadTask ->
                            if (downloadTask.isSuccessful) {
                                val url = downloadTask.result.toString()
                                val requestOptions = RequestOptions()
                                    .placeholder(R.drawable.defaultplaceholder)
                                    .error(R.drawable.error)
                                Glide.with(requireActivity())
                                    .load(url)
                                    .apply(requestOptions)
                                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                    .error(R.drawable.error)
                                    .into(imagenfoto)
                            } else {
                                Log.e("Error", "Error al obtener la URL de descarga", downloadTask.exception)
                            }
                        }
                    } else {
                        Log.e("Error", "Error al subir la imagen", task.exception)
                    }
                }
            }
        }
    }





}


