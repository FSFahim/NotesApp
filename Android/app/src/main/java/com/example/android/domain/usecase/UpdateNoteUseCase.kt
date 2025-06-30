package com.example.android.domain.usecase

import com.example.android.domain.model.Note
import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Int, note: Note) {
        repository.updateNote(id, note)
    }
}
