package com.example.android.ui.viewmodel

import android.content.Context
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.data.model.LocationNote
import com.example.android.domain.repository.LocationNoteRepository
import com.example.android.domain.usecase.InsertLocationNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationNoteViewModel @Inject constructor(
    private val insertLocationNoteUseCase: InsertLocationNoteUseCase,
    private val locationNoteRepository: LocationNoteRepository
) : ViewModel(){

    private val _locationNotes = MutableLiveData<List<LocationNote>>()
    val locationNotes : LiveData<List<LocationNote>> = _locationNotes

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    fun loadSavedLocation(){
        viewModelScope.launch {
            try {
                _locationNotes.value = locationNoteRepository.getAllNotes()
            }catch (e: Exception){
                _error.value = "Failed to load saved locations : ${e.localizedMessage}"
            }
        }
    }

    fun fetchAndSaveLocations(context: Context){
        viewModelScope.launch {
            try {
                for(i in 1..10){
                    val location = getCurrentLocation(context)
                    val locationNote = LocationNote(
                        title = "Location $i",
                        content = "Lat : ${location.latitude}, Long : ${location.longitude}"
                    )
                    insertLocationNoteUseCase(locationNote)
                    loadSavedLocation()
                    delay(2000)
                }
            }catch (e:Exception){
                _error.value = "Error fetching or saving location: ${e.localizedMessage}"
            }
        }
    }

}