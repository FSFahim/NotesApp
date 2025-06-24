package com.example.android.ui.update

import com.example.android.model.Note

interface UpdateNoteContract {
    interface View {
        fun showNote(note: Note)
        fun showSuccess()
        fun showError(message: String)
        fun close()
    }

    interface Presenter {
        fun loadNote(id: Int)
        fun updateNote(note: Note)
        fun onDestroy()
    }
}
