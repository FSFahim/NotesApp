package com.example.android.domain.repository

import com.example.android.data.model.LocationNote
import kotlinx.coroutines.flow.Flow

interface LocationNotesRepository {
    suspend fun insertLocationNote(locationNote : LocationNote)
    fun getAllLocationNotes() : Flow<List<LocationNote>>
}