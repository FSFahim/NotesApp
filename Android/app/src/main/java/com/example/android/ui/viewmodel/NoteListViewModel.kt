package com.example.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.data.model.Note
import com.example.android.data.repositroy.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadNotes() {
        repository.getAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(
                call: Call<List<Note>?>,
                response: Response<List<Note>?>
            ) {
                if (response.isSuccessful) {
                    _notes.value = response.body()
                } else {
                    _error.value = "Failed to load notes"
                }
            }

            override fun onFailure(
                call: Call<List<Note>?>,
                t: Throwable
            ) {
                _error.value = "Error: ${t.localizedMessage}"
            }

        })
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _success.value = "Note Deleted"
                    loadNotes() // Refresh list
                } else {
                    _error.value = "Delete failed"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error: ${t.localizedMessage}"
            }
        })
    }
}

