package com.example.android.ui.adapter

import com.example.android.data.model.Note

interface NoteItemListener {
    fun onNoteClicked(note: Note)
    fun onNoteEditRequested(note: Note)
    fun onNoteDeleteRequested(note: Note)
}