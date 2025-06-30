package com.example.android.data.remote

import com.example.android.domain.model.Note
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApiService {

    @GET("/notes")
    suspend fun getAllNotes(): Response<List<Note>>

    @POST("/notes")
    suspend fun addNote(@Body note: Note): Response<Unit>

    @PUT("/notes/{id}")
    suspend fun updateNote(@Path("id") id:Int, @Body note: Note): Response<Unit>

    @DELETE("/notes/{id}")
    suspend fun deleteNote(@Path("id") id:Int): Response<Unit>

    @GET("/notes/{id}")
    suspend fun getNoteById(@Path("id") id:Int): Response<Note>
}