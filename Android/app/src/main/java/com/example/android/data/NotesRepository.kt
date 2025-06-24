package com.example.android.data

import com.example.android.ApiClient
import com.example.android.model.Note
import retrofit2.Call

class NotesRepository {

    fun getAllNotes(): Call<List<Note>> {
        return ApiClient.notesService.getAllNotes()
    }

    fun deleteNoteById(id: Int): Call<Void> {
        return ApiClient.notesService.deleteNote(id)
    }

    fun getNoteById(id: Int): Call<Note> {
        return ApiClient.notesService.getNoteById(id)
    }

    // Optional: addNote(), updateNote()...
}
