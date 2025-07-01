package com.example.android.data.repositroy

import com.example.android.data.local.LocationNoteDao
import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationNotesRepositoryImpl @Inject constructor(
    private val dao : LocationNoteDao
) : LocationNotesRepository{
    override suspend fun insertLocationNote(locationNote: LocationNote) = dao.insertLocationNote(locationNote)
    override fun getAllLocationNotes(): Flow<List<LocationNote>> = dao.getAllLocationNotes()

}