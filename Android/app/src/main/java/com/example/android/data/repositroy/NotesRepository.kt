package com.example.android.data.repositroy

import com.example.android.data.model.Note
import com.example.android.data.remote.NotesApiService
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesAPI : NotesApiService
){
    suspend fun getAllNotes() : List<Note> = notesAPI.getAllNotes()
    suspend fun getNoteById(id: Int) : Note = notesAPI.getNoteById(id)
    suspend fun addNote(note: Note) = notesAPI.addNote(note)
    suspend fun updateNote(id: Int, note: Note) = notesAPI.updateNote(id, note)
    suspend fun deleteNote(id: Int) = notesAPI.deleteNote(id)
}