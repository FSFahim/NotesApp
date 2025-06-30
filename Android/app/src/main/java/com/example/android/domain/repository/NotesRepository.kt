package com.example.android.domain.repository

import com.example.android.domain.model.Note

interface NotesRepository {
    suspend fun getAllNotes() : List<Note>
    suspend fun getNoteById(id: Int) : Note
    suspend fun addNote(note: Note)
    suspend fun updateNote(id: Int, note: Note)
    suspend fun deleteNote(id: Int)
}