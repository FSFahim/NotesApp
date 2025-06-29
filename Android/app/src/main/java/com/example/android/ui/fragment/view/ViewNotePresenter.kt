package com.example.android.ui.fragment.view

import com.example.android.data.repositroy.NotesRepository
import com.example.android.data.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewNotePresenter(
    private var view: ViewNoteContract.View?,
    private val repository: NotesRepository
) : ViewNoteContract.Presenter {

    override fun loadNote(id: Int) {
        repository.getNoteById(id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful && response.body() != null) {
                    view?.showNote(response.body()!!)
                } else {
                    view?.showError("Note not found")
                    view?.close()
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                view?.showError("Error: ${t.localizedMessage}")
                view?.close()
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}
