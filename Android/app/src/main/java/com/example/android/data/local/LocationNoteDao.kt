package com.example.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.data.model.LocationNote
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationNoteDao {
    @Insert
    suspend fun insertLocationNote(locationNote: LocationNote)

    @Query("SELECT * FROM location_notes")
    fun getAllLocationNotes(): Flow<List<LocationNote>>

//    @Update
//    suspend fun updateLocationNote(note: LocationNote)
//
//    @Query("DELETE FROM location_notes WHERE id = :id")
//    suspend fun deleteLocationNoteById(id: Int)
//
//    @Query("SELECT * FROM location_notes WHERE id = :id")
//    fun getLocationNoteById(id: Int): Flow<LocationNote?>

    @Query("DELETE FROM location_notes")
    suspend fun deleteAll()


}