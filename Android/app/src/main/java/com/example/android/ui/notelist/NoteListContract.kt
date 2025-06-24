package com.example.android.ui.notelist

import com.example.android.model.Note

interface NoteListContract {
    interface View {
        fun showNotes(notes: List<Note>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadNotes()
        fun onDestroy()
        fun deleteNote(note: Note)
    }
}