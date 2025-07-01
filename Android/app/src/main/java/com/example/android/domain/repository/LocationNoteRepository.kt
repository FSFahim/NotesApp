package com.example.android.domain.repository

import com.example.android.data.model.LocationNote
import kotlinx.coroutines.flow.Flow

interface LocationNoteRepository {
    suspend fun insertNote(locationNote : LocationNote)
    suspend fun getAllNotes() : Flow<List<LocationNote>>
}