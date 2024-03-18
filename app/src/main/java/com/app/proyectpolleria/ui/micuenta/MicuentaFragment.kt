package com.app.proyectpolleria.ui.micuenta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.proyectpolleria.Activity_login
import com.app.proyectpolleria.databinding.FragmentMicuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MicuentaFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var cardview: CardView
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnEditar: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
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

        cardview=binding.selectedContactCardView2
        btnCerrarSesion=binding.btnCerrarSesion
        btnEditar=binding.btnEditar
        btnGuardar=binding.btnGuardar
        btnCancelar=binding.btnCancelar
        vusuario=binding.editUsuario
        vnombre=binding.editNombre
        vapellido=binding.editApellido
        vtelefono=binding.editTelefono
        vdireccion=binding.editDireccion
        usuario=binding.usuario
        nombre=binding.nombres
        apellido=binding.apellidos
        telefono=binding.telefono
        correo=binding.correo
        direccion=binding.direccion



        editar()
        guardar()
        cerrarSesion()


        // Obtener el correo electrónico del usuario actual
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email

            correo.text = userEmail
            // Obtener SharedPreferences utilizando el correo electrónico como clave
            val preferences = requireContext().getSharedPreferences("userData_$userEmail", Context.MODE_PRIVATE)

            // Mostrar datos en los TextViews
            usuario.text = preferences.getString("usuario", "")
            nombre.text = preferences.getString("nombre", "")
            apellido.text = preferences.getString("apellido", "")
            telefono.text = preferences.getString("telefono", "")
            direccion.text = preferences.getString("direccion", "")
        }

        btnCancelar.setOnClickListener {
            cardview.setVisibility(
                View.GONE
            )
        }

        return root
    }

    private fun cerrarSesion(){
        btnCerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
// Redirigir al usuario a la pantalla de inicio de sesión
            val intent = Intent(requireContext(), Activity_login::class.java)
// Limpiar la pila de actividades para que el usuario no pueda regresar usando el botón "Atrás"
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish() // Cerrar la actividad actual

        }
    }

    private fun editar(){
        btnEditar.setOnClickListener{
                cardview.setVisibility(
                    View.VISIBLE
                )
        }
    }

    private fun guardar(){
        btnGuardar.setOnClickListener {

                // Lógica para guardar datos en Firestore
                val userData = hashMapOf(
                    "usuario" to vusuario.text.toString(),
                    "nombre" to vnombre.text.toString(),
                    "apellido" to vapellido.text.toString(),
                    "telefono" to vtelefono.text.toString(),
                    "direccion" to vdireccion.text.toString()
                )

                // Obtener el correo electrónico del usuario actual
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let { user ->
                    val userEmail = user.email

                    // Guardar los datos en Firestore usando el correo electrónico como identificador del documento
                    userEmail?.let { email ->
                        db.collection("users").document(email).set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                                //mandar los datos al cardview de perfil
                                usuario.text = userData["usuario"].toString()
                                nombre.text = userData["nombre"].toString()
                                apellido.text = userData["apellido"].toString()
                                telefono.text = userData["telefono"].toString()
                                direccion.text = userData["direccion"].toString()

                                // Guardar los datos en SharedPreferences
                                val preferences = requireContext().getSharedPreferences("userData_$email", Context.MODE_PRIVATE)
                                val editor = preferences.edit()
                                editor.putString("usuario", userData["usuario"].toString())
                                editor.putString("nombre", userData["nombre"].toString())
                                editor.putString("apellido", userData["apellido"].toString())
                                editor.putString("telefono", userData["telefono"].toString())
                                editor.putString("direccion", userData["direccion"].toString())
                                editor.apply()
                                cardview.visibility = View.GONE
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
