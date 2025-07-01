package com.example.android.domain.usecase

import com.example.android.data.model.Note
import com.example.android.domain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.addNote(note)
    }
}
