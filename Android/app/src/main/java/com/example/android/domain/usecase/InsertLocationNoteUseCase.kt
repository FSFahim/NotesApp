package com.example.android.domain.usecase

import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNoteRepository
import javax.inject.Inject

class InsertLocationNoteUseCase @Inject constructor(
    private val repository: LocationNoteRepository
) {
    suspend operator fun invoke(note: LocationNote){
        repository.insertNote(note)
    }
}