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
        viewModelScope.launch {
            try {
                val noteList = repository.getAllNotes()
                _notes.value = noteList
            } catch (e: Exception) {
                _error.value = "Failed to load notes: ${e.localizedMessage}"
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(id)
                _success.value = "Note Deleted"
                loadNotes() // Refresh list after deletion
            } catch (e: Exception) {
                _error.value = "Delete failed: ${e.localizedMessage}"
            }
        }
    }
}

