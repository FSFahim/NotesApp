package com.example.android.ui.fragment.add

import com.example.android.data.model.Note

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
