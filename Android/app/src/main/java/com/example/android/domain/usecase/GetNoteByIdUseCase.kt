package com.example.android.domain.usecase

import com.example.android.domain.model.Note
import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Int): Note {
        return repository.getNoteById(id)
    }
}
