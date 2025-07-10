package com.example.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.first

@HiltViewModel
class LocationNoteListViewModel @Inject constructor(
    private val locationNotesRepository: LocationNotesRepository
): ViewModel() {
    val locationNotesFlow: Flow<List<LocationNote>> = locationNotesRepository.getAllLocationNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uploadStatus = MutableLiveData<String>()
    val uploadStatus: LiveData<String> = _uploadStatus

    private suspend fun getAllLocationNotesOnce(): List<LocationNote> {
        return locationNotesRepository.getAllLocationNotes().first()
    }

    fun uploadAndClearLocationNotes() {
        viewModelScope.launch {
            try {
                // Collect current notes (assuming repository returns Flow)
                val notes = locationNotesRepository.getAllLocationNotes().stateIn(viewModelScope).value
                if (notes.isEmpty()) {
                    _uploadStatus.postValue("No notes to upload")
                    return@launch
                }

                val success = locationNotesRepository.uploadLocationNotesToServer(notes)
                if (success) {
                    locationNotesRepository.deleteAllLocationNotes()
                    _uploadStatus.postValue("Upload successful, local notes cleared")
                } else {
                    _uploadStatus.postValue("Upload failed")
                }
            } catch (e: Exception) {
                _uploadStatus.postValue("Error: ${e.localizedMessage}")
            }
        }
    }
}