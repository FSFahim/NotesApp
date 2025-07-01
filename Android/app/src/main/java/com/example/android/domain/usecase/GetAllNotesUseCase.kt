package com.example.android.domain.usecase

import com.example.android.data.model.Note
import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NotesRepository
){
    suspend operator fun invoke() : List<Note> {
        return repository.getAllNotes()
    }
}