package com.example.android.ui.add

import com.example.android.model.Note

interface AddNoteContract {
    interface View {
        fun showSuccess()
        fun showError(message: String)
    }

    interface Presenter {
        fun saveNote(note: Note)
        fun onDestroy()
    }
}
