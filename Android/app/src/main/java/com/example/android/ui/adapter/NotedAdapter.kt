package com.example.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.data.model.Note

interface NoteItemListener {
    fun onNoteClicked(note: Note)
    fun onNoteEditRequested(note: Note)
    fun onNoteDeleteRequested(note: Note)
}

class NotesAdapter(
    private var notes: List<Note>,
    private val listener: NoteItemListener
): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    //val db : NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            listener.onNoteEditRequested(note)
        }

        holder.itemView.setOnClickListener {
            listener.onNoteClicked(note)
        }

        holder.deleteButton.setOnClickListener {
            listener.onNoteDeleteRequested(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun refreshData(newNotes:List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}