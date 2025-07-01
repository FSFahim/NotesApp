package com.example.android.domain.usecase

import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNotesRepository
import javax.inject.Inject

class InsertLocationNoteUseCase @Inject constructor(
    private val repository: LocationNotesRepository
) {
    suspend operator fun invoke(note: LocationNote){
        repository.insertLocationNote(note)
    }
}