package com.example.android.data.repositroy

import com.example.android.domain.model.Note
import com.example.android.data.remote.NotesApiService
import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesAPI : NotesApiService
) : NotesRepository{
    override suspend fun getAllNotes() : List<Note> {
        val response = notesAPI.getAllNotes()
        if(response.isSuccessful){
            return response.body() ?: emptyList()
        }else{
            throw Exception("Failed to load notes: ${response.code()}")
        }
    }
    override suspend fun getNoteById(id: Int) : Note {
      val response = notesAPI.getNoteById(id)
      if(response.isSuccessful){
          return response.body() ?: throw Exception("Note not found")
      }else{
          throw Exception("Failed to get note: ${response.code()}")
      }
    }
    override suspend fun addNote(note: Note) {
        val response = notesAPI.addNote(note)
        if (!response.isSuccessful) {
            throw Exception("Failed to add note: ${response.code()}")
        }
    }

    override suspend fun updateNote(id: Int, note: Note) {
        val response = notesAPI.updateNote(id, note)
        if (!response.isSuccessful) {
            throw Exception("Failed to update note: ${response.code()}")
        }
    }

    override suspend fun deleteNote(id: Int) {
        val response = notesAPI.deleteNote(id)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete note: ${response.code()}")
        }
    }
}