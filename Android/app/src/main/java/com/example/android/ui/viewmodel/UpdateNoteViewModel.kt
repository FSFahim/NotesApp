package com.example.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.model.Note
import com.example.android.data.repositroy.NotesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val repository: NotesRepositoryImpl
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadNote(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getNoteById(id)
                _note.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load note: ${e.localizedMessage}"
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.updateNote(note.id!!, note)
                _success.value = true
            } catch (e: Exception) {
                _error.value = "Update failed: ${e.localizedMessage}"
            }
        }
    }
}
