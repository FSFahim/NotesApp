package com.example.android

import retrofit2.http.*
import retrofit2.Call

interface NotesApiService {

    @GET("/notes")
    fun getAllNotes(): Call<List<Note>>

    @POST("/Notes")
    fun addNote(@Body note: Note): Call<Void>

    @PUT("/Notes/{id}")
    fun updateNote(@Path("id") id:Int, @Body note:Note): Call<Void>

    @DELETE("/notes/{id}")
    fun deleteNote(@Path("id") id:Int): Call<Void>

    @GET("/notes/{id}")
    fun getNoteById(@Path("id") id:Int): Call<Note>
}