package com.example.android.domain.usecase

import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLocationNotesUseCase @Inject constructor(
    private val locationNotesRepository: LocationNotesRepository
){
    operator fun invoke() : Flow<List<LocationNote>> = locationNotesRepository.getAllLocationNotes()
}