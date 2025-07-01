package com.example.android.data.repositroy

import com.example.android.data.local.LocationNoteDao
import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationNoteRepositoryImpl @Inject constructor(
    private val dao : LocationNoteDao
) : LocationNoteRepository{
    override suspend fun insertNote(locationNote: LocationNote) = dao.insertNote(locationNote)

    override suspend fun getAllNotes(): Flow<List<LocationNote>> = dao.getAll()

}