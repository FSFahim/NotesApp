package com.example.android.data

import com.example.android.data.remote.ApiClient
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

    fun addNote(note: Note): Call<Void> {
        return ApiClient.notesService.addNote(note)
    }

    fun updateNote(id: Int, note: Note): Call<Void> {
        return ApiClient.notesService.updateNote(id, note)
    }

}
