package com.example.android.ui.main

import com.example.android.model.Note

interface MainContract {
    interface View {
        fun showNotes(notes: List<Note>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadNotes()
        fun onDestroy()
    }
}
