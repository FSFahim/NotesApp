package com.example.android.data.remote

import com.example.android.data.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApiService {

    @GET("/notes")
    suspend fun getAllNotes(): List<Note>

    @POST("/Notes")
    suspend fun addNote(@Body note: Note): Void

    @PUT("/Notes/{id}")
    suspend fun updateNote(@Path("id") id:Int, @Body note: Note): Void

    @DELETE("/notes/{id}")
    suspend fun deleteNote(@Path("id") id:Int): Void

    @GET("/notes/{id}")
    suspend fun getNoteById(@Path("id") id:Int): Note
}