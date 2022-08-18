package com.frametrap.app

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.nio.channels.AsynchronousFileChannel.open

class CharacterRecyclerViewAdapter(private var characterlist: ArrayList<CharacterModel>): RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

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
        holder.textView.text = characterlist[position].name_West
    }

    override fun getItemCount(): Int {
        return characterlist.size
    }

    fun update(list: ArrayList<CharacterModel>) {
        characterlist.clear()
        characterlist.addAll(list)
        notifyDataSetChanged() //important updates the UI
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.charactertextView)
        //val imageView: ImageView = itemView.findViewById(R.id.character_image)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}