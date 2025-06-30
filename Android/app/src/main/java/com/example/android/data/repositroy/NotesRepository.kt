package com.example.android.data.repositroy

import com.example.android.data.model.Note
import com.example.android.data.remote.NotesApiService
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesAPI : NotesApiService
){
    fun getAllNotes() = notesAPI.getAllNotes()
    fun getNoteById(id: Int) = notesAPI.getNoteById(id)
    fun addNote(note: Note) = notesAPI.addNote(note)
    fun updateNote(id: Int, note: Note) = notesAPI.updateNote(id, note)
    fun deleteNote(id: Int) = notesAPI.deleteNote(id)
}