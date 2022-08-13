package com.frametrap.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterRecyclerViewAdapter(private val characterfileslist: Array<String>?): RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

    private lateinit var clicklistener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        clicklistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_list_row, parent, false)
        return ViewHolder(view, clicklistener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = characterfileslist?.get(position)?.dropLast(4)
    }

    override fun getItemCount(): Int {
        return characterfileslist!!.size
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.charactertextView)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}