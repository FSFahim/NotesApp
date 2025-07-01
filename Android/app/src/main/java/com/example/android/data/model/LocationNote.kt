package com.example.android.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_notes")
data class LocationNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val timeStamp : Long = System.currentTimeMillis()
)
