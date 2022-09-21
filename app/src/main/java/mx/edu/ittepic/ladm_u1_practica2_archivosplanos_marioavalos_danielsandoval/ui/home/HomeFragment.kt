package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.ui.home

import android.R
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.Disco
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.DiscoAdapter
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.databinding.FragmentHomeBinding
import java.lang.reflect.Type
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception

class HomeFragment : Fragment(),DiscoAdapter.OnItemClickListener{

    var dataSet: MutableList<Disco> = arrayListOf()
    var listaDatosDisco = ArrayList<String>()
    //lateinit var dataList:ArrayList<Disco>
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

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        mAdapter = DiscoAdapter(this)
        binding.recyclerDiscoList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDiscoList.adapter = mAdapter

        abrirDesdeArchivo()
        //actualizar()

        binding.btnGuardar.setOnClickListener{
            if (!isEditar){
                listaDatosDisco.add(binding.editDisco.text.toString())
                mAdapter.submitList(listaDatosDisco)
                mAdapter.notifyDataSetChanged()
                binding.editDisco.setText("")
                guardarEnArchivo()
            }else{
                listaDatosDisco[posicion] = binding.editDisco.text.toString()
                posicion = -1
                isEditar = false
                mAdapter.submitList(listaDatosDisco)
                mAdapter.notifyDataSetChanged()
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
        binding.editDisco.setText(item.toString())
    }

    override fun onItemBorrar(position: Int) {
        listaDatosDisco.removeAt(position)
        mAdapter.submitList(listaDatosDisco)
        mAdapter.notifyDataSetChanged()
    }

    /*private fun insertar() {
        /*var concatenacion = binding.nombrecontacto.text.toString()+"\n"+
                binding.telefono.text.toString()*/
        listaDatosDisco.add(binding.editDisco.text.toString()+"\n")
        binding.recyclerDiscoList.adapter = ArrayAdapter<String>(binding.root.context, android.R.layout.)
        binding.nombrecontacto.setText("")
    }*/

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

            Toast.makeText(context,"SE GUARDO CORRECTAMENTE", Toast.LENGTH_LONG).show()

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
            AlertDialog.Builder(binding.root.context)
                .setTitle("ERROR")
                .setMessage(e.message)
                .setPositiveButton("OK"){d,i->}
                .show()
        }
    }
}