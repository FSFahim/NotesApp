package com.example.android.data.remote

import com.example.android.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApiService {

    @GET("/notes")
    fun getAllNotes(): Call<List<Note>>

    @POST("/Notes")
    fun addNote(@Body note: Note): Call<Void>

    @PUT("/Notes/{id}")
    fun updateNote(@Path("id") id:Int, @Body note: Note): Call<Void>

    @DELETE("/notes/{id}")
    fun deleteNote(@Path("id") id:Int): Call<Void>

    @GET("/notes/{id}")
    fun getNoteById(@Path("id") id:Int): Call<Note>
}