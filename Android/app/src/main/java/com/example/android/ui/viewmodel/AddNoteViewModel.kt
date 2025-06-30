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
class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun saveNote(note: Note) {
        repository.addNote(note).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _success.value = true
                } else {
                    _error.value = "Failed to save note"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _error.value = "Error: ${t.localizedMessage}"
            }
        })
    }
}
