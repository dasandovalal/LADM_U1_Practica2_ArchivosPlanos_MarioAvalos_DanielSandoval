package mx.edu.ittepic.ladm_u1_practica2_archivosplanos_marioavalos_danielsandoval

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DiscoAdapter(private val itemClickListener: OnItemClickListener): ListAdapter<Disco,DiscoAdapter.ViewHolder>(DiffCallBack()){

    private class DiffCallBack:DiffUtil.ItemCallback<Disco>(){
        override fun areItemsTheSame(oldItem: Disco, newItem: Disco): Boolean {
            return oldItem.disco == newItem.disco
        }

        override fun areContentsTheSame(oldItem: Disco, newItem: Disco): Boolean {
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

        fun bind(item:Disco, clickListener:OnItemClickListener){
            txtDisc.text = item.disco

            btnEdita.setOnClickListener { clickListener.onItemEditar(adapterPosition, item) }
            btnBorra.setOnClickListener { clickListener.onItemBorrar(adapterPosition) }
        }
    }

    interface OnItemClickListener{
        fun onItemEditar(position: Int, item: Disco)
        fun onItemBorrar(position: Int)
    }
}