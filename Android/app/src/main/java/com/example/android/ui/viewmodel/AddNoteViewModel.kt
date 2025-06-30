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
class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepositoryImpl
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun saveNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.addNote(note)
                _success.value = true
            } catch (e: Exception) {
                _error.value = "Add note failed: ${e.localizedMessage}"
            }
        }
    }
}
