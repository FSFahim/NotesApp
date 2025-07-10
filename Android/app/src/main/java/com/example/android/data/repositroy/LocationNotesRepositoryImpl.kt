package com.example.android.data.repositroy

import com.example.android.data.local.LocationNoteDao
import com.example.android.data.model.LocationNote
import com.example.android.data.remote.NotesApiService
import com.example.android.domain.repository.LocationNotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationNotesRepositoryImpl @Inject constructor(
    private val locationNoteDao: LocationNoteDao,
    private val notesApiService: NotesApiService
) : LocationNotesRepository{
    override suspend fun insertLocationNote(locationNote: LocationNote) = locationNoteDao.insertLocationNote(locationNote)
    override fun getAllLocationNotes(): Flow<List<LocationNote>> = locationNoteDao.getAllLocationNotes()
//    override fun getLocationNoteById(id: Int): Flow<LocationNote?> = dao.getLocationNoteById(id)
//    override suspend fun updateLocationNote(note: LocationNote) = dao.updateLocationNote(note)
//    override suspend fun deleteLocationNoteById(id: Int) = dao.deleteLocationNoteById(id)
    override suspend fun uploadLocationNotesToServer(notes: List<LocationNote>): Boolean {
        val response = notesApiService.uploadLocationNotes(notes)
        return response.isSuccessful
    }

    override suspend fun deleteAllLocationNotes() {
        locationNoteDao.deleteAll()
    }
}