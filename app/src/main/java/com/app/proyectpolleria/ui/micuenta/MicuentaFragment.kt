package com.app.proyectpolleria.ui.micuenta

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.proyectpolleria.Activity_login
import com.app.proyectpolleria.ComprasActivity
import com.app.proyectpolleria.DatosActivity
import com.app.proyectpolleria.Negocio.UsuarioNegocio
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

    private lateinit var btnCerrarSesion: LinearLayout
    private lateinit var btnCompras: LinearLayout
    private lateinit var btnPolitica: LinearLayout
    private lateinit var btnEditar: LinearLayout
    private lateinit var  txtnombre: TextView
    private  lateinit var  galery : CardView
    private lateinit var  imagenfoto : ImageView
    private val clNegocio = UsuarioNegocio()
    private var _binding: FragmentMicuentaBinding? = null
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var idUsuario:kotlin.Int = 0

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onResume()
        val micuentaViewModel =
            ViewModelProvider(this).get(MicuentaViewModel::class.java)

        _binding = FragmentMicuentaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        galery = binding.imgboton;
        imagenfoto = root.findViewById(R.id.imgFoto)
        btnEditar = root.findViewById(R.id.linedit)
        btnCompras = root.findViewById(R.id.linecompras)
        btnPolitica = root.findViewById(R.id.linepolitica)
        btnCerrarSesion = root.findViewById(R.id.linesalir)
        imagenfoto.setScaleType(ImageView.ScaleType.CENTER_CROP)
        txtnombre = root.findViewById(R.id.txtUsuario)

        val preferences = requireActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
        idUsuario = preferences.getInt("USUARIO", 0)
        verDatos(idUsuario)


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
                                GuardarImagen(url)
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


    private fun GuardarImagen( urlImagen: String){
        val exitoso = clNegocio.GuardarImagen(idUsuario , urlImagen)
        if(exitoso == true){
            VerImagen(urlImagen)
        }else{
            Toast.makeText(activity, "Hubo error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }


    }

    private fun verDatos(idUsuario: Int) {
        val usuarios = clNegocio.recibirDatos(idUsuario)
        if (usuarios.isNotEmpty()) {
            val usuario = usuarios[0] // Suponiendo que solo deseas obtener el primer usuario de la lista

            val nombre = usuario.nombre
            val apellido = usuario.apellido
            val urlImagen = usuario.img

            txtnombre.setText(nombre )
            if(urlImagen != null){
                VerImagen(urlImagen)
            }

        } else {
            Toast.makeText(activity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }



    private fun VerImagen(urlImagen: String) {
        if (urlImagen.isNotBlank()) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.defaultplaceholder)
                .error(R.drawable.img)
            Glide.with(requireActivity())
                .load(urlImagen)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img)
                .into(imagenfoto)
        }
    }








}


