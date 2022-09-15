package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.Disco
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.DiscoAdapter
import mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),DiscoAdapter.OnItemClickListener{

    var dataSet: MutableList<Disco> = arrayListOf()
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

        binding.btnGuardar.setOnClickListener{
            if (!isEditar){
                dataSet.add(Disco(binding.editDisco.text.toString().trim()))
                mAdapter.submitList(dataSet)
                mAdapter.notifyDataSetChanged()
                binding.editDisco.setText("")
            }else{
                dataSet[posicion].disco = binding.editDisco.text.toString()
                posicion = -1
                isEditar = false
                mAdapter.submitList(dataSet)
                mAdapter.notifyDataSetChanged()
                binding.editDisco.setText("")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemEditar(position: Int, item: Disco) {
        isEditar = true
        posicion = position
        binding.editDisco.setText(item.disco)
    }

    override fun onItemBorrar(position: Int) {
        dataSet.removeAt(position)
        mAdapter.submitList(dataSet)
        mAdapter.notifyDataSetChanged()
    }
}