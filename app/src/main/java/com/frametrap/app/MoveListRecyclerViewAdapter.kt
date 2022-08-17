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


class MoveListRecyclerViewAdapter(private var movelist: ArrayList<MoveModel>): RecyclerView.Adapter<MoveListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.move_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movenametextView.text = movelist[position].movename
        //val startup: String = movelist[position].startup
        /*when(startup.contains(";")) {
            false -> {
                holder.startuptextView.text = startup
            }
            else -> run {
                val versions = startup.split(";").toTypedArray()
                for (version in versions) {
                    Spanned spanned = Html.fromHtml(textWithMarkup)
                }
            }
        }*/
        holder.startuptextView.text = movelist[position].startup.replace(";","\n").replace("?","\t")
        holder.activetextView.text = movelist[position].active.replace(";","\n").replace("?","\t")
        holder.recoverytextView.text = movelist[position].recovery.replace(";","\n").replace("?","\t")
        holder.onblocktextView.text = movelist[position].onBlock.replace(";","\n").replace("?","\t")
        holder.movetypetextView.text = movelist[position].movetype.replace(";","\n").replace("?","\t")
    }

    override fun getItemCount(): Int {
        return movelist.size
    }

    fun filterList(filteredList : ArrayList<MoveModel>){
        movelist = filteredList
        notifyDataSetChanged()
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