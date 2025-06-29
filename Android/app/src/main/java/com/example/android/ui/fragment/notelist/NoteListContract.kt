package com.example.android.ui.fragment.notelist

import com.example.android.data.model.Note

interface NoteListContract {
    interface View {
        fun showNotes(notes: List<Note>)
        fun showError(message: String)
        fun showSuccess()
    }

    interface Presenter {
        fun loadNotes()
        fun onDestroy()
        fun deleteNote(note: Note)
    }
}