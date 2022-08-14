package com.frametrap.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoveListRecyclerViewAdapter(private val movelist: ArrayList<MoveModel>, private val movenamewidth: Int, private val startupwidth: Int, private val activewidth: Int, private val recoverywidth: Int, private val onblockwidth: Int): RecyclerView.Adapter<MoveListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.move_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movenametextView.text = movelist[position].movename
        holder.movenametextView.width = 15
        holder.startuptextView.text = movelist[position].startup
        holder.startuptextView.width = 15
        holder.activetextView.text = movelist[position].active
        holder.activetextView.width = 15
        holder.recoverytextView.text = movelist[position].recovery
        holder.recoverytextView.width = 15
        holder.onblocktextView.text = movelist[position].onBlock
        holder.onblocktextView.width = 15
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
    }
}