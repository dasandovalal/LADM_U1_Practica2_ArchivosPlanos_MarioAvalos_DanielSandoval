package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DiscoAdapter(private val itemClickListener: OnItemClickListener): ListAdapter<String,DiscoAdapter.ViewHolder>(DiffCallBack()){

    private class DiffCallBack:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val txtDisc = itemView.findViewById<TextView>(R.id.txtDisco)
        private val btnEdita = itemView.findViewById<ImageButton>(R.id.btnEditar)
        private val btnBorra = itemView.findViewById<ImageButton>(R.id.btnBorrar)

        fun bind(item:String, clickListener:OnItemClickListener){
            txtDisc.text = item.toString()

            btnEdita.setOnClickListener { clickListener.onItemEditar(adapterPosition, item.toString()) }
            btnBorra.setOnClickListener { clickListener.onItemBorrar(adapterPosition) }
        }
    }

    interface OnItemClickListener{
        fun onItemEditar(position: Int, item: String)
        fun onItemBorrar(position: Int)
    }
}