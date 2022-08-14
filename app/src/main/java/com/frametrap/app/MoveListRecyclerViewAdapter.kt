package com.frametrap.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoveListRecyclerViewAdapter(private val movelist: ArrayList<MoveModel>): RecyclerView.Adapter<MoveListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.move_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movenametextView.text = movelist[position].movename
        holder.startuptextView.text = movelist[position].startup
        holder.activetextView.text = movelist[position].active
        holder.recoverytextView.text = movelist[position].recovery
        holder.onblocktextView.text = movelist[position].onBlock
        holder.movetypetextView.text = movelist[position].movetype
    }

    override fun getItemCount(): Int {
        return movelist.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val movenametextView: TextView = itemView.findViewById(R.id.move_name_textView)
        val startuptextView: TextView = itemView.findViewById(R.id.startup_textView)
        val activetextView: TextView = itemView.findViewById(R.id.active_textView)
        val recoverytextView: TextView = itemView.findViewById(R.id.recovery_textView)
        val onblocktextView: TextView = itemView.findViewById(R.id.onblock_textView)
        val movetypetextView: TextView = itemView.findViewById(R.id.move_type_textView)

    }
}