package com.example.android.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.data.local.LocationNoteDao
import com.example.android.data.model.LocationNote

@Database(
    entities = [LocationNote::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun locationNoteDao(): LocationNoteDao
}