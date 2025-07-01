package com.example.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android.data.model.LocationNote
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationNoteDao {
    @Insert
    suspend fun insertNote(locationNote: LocationNote)

    @Query("SELECT * FROM location_notes ORDER BY timeStamp DESC")
    suspend fun getAll(): Flow<List<LocationNote>>
}