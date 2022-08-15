package com.frametrap.app

import android.content.Context
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoveListRecyclerViewAdapter(private val movelist: ArrayList<MoveModel>): RecyclerView.Adapter<MoveListRecyclerViewAdapter.ViewHolder>() {

    private lateinit var cont: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.move_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movenametextView.text = movelist[position].movename
        val startup: String = movelist[position].startup
        /*when(startup.contains(";")){
            false -> {holder.startuptextView.text = startup}
            else -> run {
                val versions = startup.split(";").toTypedArray()
                for (version in versions){
                    //val textView: TextView = TextView(parent)
                    //textView.text=version
                    //holder.subversions.addView(textView)
                }
            }
        }*/
        val new: String = startup.replace(";","\n")
        holder.startuptextView.text = new.replace("?","\t")
        holder.activetextView.text = movelist[position].active
        holder.recoverytextView.text = movelist[position].recovery
        holder.onblocktextView.text = movelist[position].onBlock
        holder.movetypetextView.text = movelist[position].movetype
        holder.moveimageView.setImageResource(R.drawable.ic_launcher_background)
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
        val moveimageView: ImageView = itemView.findViewById(R.id.move_imageView)

    }
}