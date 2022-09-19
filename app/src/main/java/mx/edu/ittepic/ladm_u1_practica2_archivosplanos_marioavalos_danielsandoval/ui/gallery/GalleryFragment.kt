package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.databinding.FragmentGalleryBinding
import java.io.*

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.guardarArchivo.setOnClickListener {
            guardarEnArchivo(binding.tituloArchivo.text.toString())
        }

        binding.abrirArchivo.setOnClickListener {
            abrirContenidoArchivo()
        }

/*
        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
 */
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun guardarEnArchivo(mensaje:String){
        try {
            var archivo = OutputStreamWriter(context?.openFileOutput("datos.txt", Context.MODE_PRIVATE))
            archivo.write(mensaje)
            archivo.flush()
            archivo.close()
            val applicationContext = activity?.applicationContext
            Toast.makeText(applicationContext,"Se guardo correctamente",Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            val applicationContext = activity?.applicationContext
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
        }
    }

    fun abrirContenidoArchivo(){
        try{
            val archivo = BufferedReader(InputStreamReader(context?.openFileInput("datos.txt")))
            binding.verArchivo.setText((archivo.readLine()))
            archivo.close()
        }catch (e:Exception){
            val applicationContext = activity?.applicationContext
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
        }
    }
}