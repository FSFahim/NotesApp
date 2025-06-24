package com.example.android.ui.view

import com.example.android.model.Note

interface ViewNoteContract {
    interface View {
        fun showNote(note: Note)
        fun showError(message: String)
        fun close()
    }

    interface Presenter {
        fun loadNote(id: Int)
        fun onDestroy()
    }
}
