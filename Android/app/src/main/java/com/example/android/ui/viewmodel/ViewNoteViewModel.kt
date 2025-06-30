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
class ViewNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadNote(id: Int) {
        repository.getNoteById(id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful && response.body() != null) {
                    _note.value = response.body()
                } else {
                    _error.value = "Note not found"
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                _error.value = "Error: ${t.localizedMessage}"
            }
        })
    }
}
