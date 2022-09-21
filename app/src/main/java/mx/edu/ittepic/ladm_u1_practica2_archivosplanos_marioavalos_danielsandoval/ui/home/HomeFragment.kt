package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.DiscoAdapter
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.databinding.FragmentHomeBinding
import androidx.appcompat.app.AlertDialog
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception

class HomeFragment : Fragment(),DiscoAdapter.OnItemClickListener{

    var listaDatosDisco = ArrayList<String>()
    lateinit var mAdapter: DiscoAdapter
    var isEditar = false
    var posicion = -1

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mAdapter = DiscoAdapter(this)
        binding.recyclerDiscoList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDiscoList.adapter = mAdapter

        abrirDesdeArchivo()

        binding.btnGuardar.setOnClickListener{
            var concatenacion = binding.editArtista.text.toString()+" - "+
                    binding.editDisco.text.toString()
            if (!isEditar){
                listaDatosDisco.add(concatenacion)
                mAdapter.submitList(listaDatosDisco)
                mAdapter.notifyDataSetChanged()
                binding.editArtista.setText("")
                binding.editDisco.setText("")
                guardarEnArchivo()
            }else{
                listaDatosDisco[posicion] = concatenacion
                posicion = -1
                isEditar = false
                mAdapter.submitList(listaDatosDisco)
                mAdapter.notifyDataSetChanged()
                binding.editArtista.setText("")
                binding.editDisco.setText("")
                guardarEnArchivo()
            }
        }

        return root
    }

    fun actualizar(){
        mAdapter.submitList(listaDatosDisco)
        mAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemEditar(position: Int, item: String) {
        isEditar = true
        posicion = position
        var temporal = listaDatosDisco.get(posicion).split(" - ")
        binding.editArtista.setText(temporal[0])
        binding.editDisco.setText(temporal[1])
        //binding.editDisco.setText(item.toString())
    }

    override fun onItemBorrar(position: Int) {
        listaDatosDisco.removeAt(position)
        mAdapter.submitList(listaDatosDisco)
        mAdapter.notifyDataSetChanged()
        guardarEnArchivo()
    }

    fun guardarEnArchivo(){
        try{
            var archivo = OutputStreamWriter(
                binding.root.context.openFileOutput("datos.txt", MODE_PRIVATE))
            var bufferContenido = ""

            for (dato in listaDatosDisco){
                bufferContenido+= dato+"&&"
            }
            bufferContenido = bufferContenido.substring(0,
                bufferContenido.lastIndexOf("&&"))
            archivo.write(bufferContenido)
            archivo.flush()
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(binding.root.context)
                .setTitle("ERROR")
                .setMessage(e.message)
                .setPositiveButton("OK"){d,i->}
                .show()
        }
    }

    private fun abrirDesdeArchivo() {
        try {
            var archivo = BufferedReader(
                InputStreamReader(
                binding.root.context.openFileInput("datos.txt"))
            )
            var bufferContenido = ""
            var interactivo = archivo.lineSequence().iterator()
            while (interactivo.hasNext()){
                bufferContenido+=interactivo.next()
            }
            var vector = bufferContenido.split("&&")
            listaDatosDisco.clear()
            for (v in vector){
                listaDatosDisco.add(v)
            }
            mAdapter.submitList(listaDatosDisco)
            mAdapter.notifyDataSetChanged()

        }catch (e:Exception){

        }
    }
}