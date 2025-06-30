package com.example.android.domain.usecase

import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteNote(id)
    }
}
