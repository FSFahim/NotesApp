package com.example.android.ui.fragment.view

import com.example.android.data.model.Note

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
