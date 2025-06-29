package com.example.android.ui.fragment.add

import com.example.android.data.repository.NotesRepository
import com.example.android.data.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNotePresenter(
    private var view: AddNoteContract.View?,
    private val repository: NotesRepository
) : AddNoteContract.Presenter {

    override fun saveNote(note: Note) {
        repository.addNote(note).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    view?.showSuccess()
                } else {
                    view?.showError("Failed to save note")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view?.showError("Error: ${t.localizedMessage}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}
