package com.example.android.domain.repository

import com.example.android.data.model.LocationNote
import kotlinx.coroutines.flow.Flow

interface LocationNotesRepository {
    suspend fun insertLocationNote(locationNote : LocationNote)
    fun getAllLocationNotes() : Flow<List<LocationNote>>
//    fun getLocationNoteById(id: Int): Flow<LocationNote?>
//
//    suspend fun updateLocationNote(note: LocationNote)
//
//    suspend fun deleteLocationNoteById(id: Int)
    suspend fun uploadLocationNotesToServer(notes: List<LocationNote>): Boolean
    suspend fun deleteAllLocationNotes()
}