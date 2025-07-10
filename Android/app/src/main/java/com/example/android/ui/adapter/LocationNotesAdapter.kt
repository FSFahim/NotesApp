package com.example.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.data.model.LocationNote
import androidx.recyclerview.widget.ListAdapter

class LocationNotesAdapter (
    private val listener: LocationNotesItemListener
) : ListAdapter<LocationNote, LocationNotesAdapter.LocationNoteViewHolder>(LocationNoteDiffCallback()){
    class LocationNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.contentTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationNoteViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return LocationNoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder:LocationNoteViewHolder,
        position: Int
    ){
        val note = getItem(position)
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

//        holder.itemView.setOnClickListener{
//            listener.onNoteClicked(note)
//        }
    }
}