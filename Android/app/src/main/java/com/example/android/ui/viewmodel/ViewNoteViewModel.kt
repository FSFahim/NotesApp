package com.example.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.model.Note
import com.example.android.data.repositroy.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            try {
                val result = repository.getNoteById(id)
                _note.value = result
            } catch (e: Exception) {
                _error.value = "Note not found: ${e.localizedMessage}"
            }
        }
    }
}
