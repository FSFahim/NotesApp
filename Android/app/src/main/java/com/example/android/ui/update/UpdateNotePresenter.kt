package com.example.android.ui.update

import com.example.android.data.NotesRepository
import com.example.android.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateNotePresenter(
    private var view: UpdateNoteContract.View?,
    private val repository: NotesRepository
) : UpdateNoteContract.Presenter {

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

    override fun updateNote(note: Note) {
        repository.updateNote(note.id!!, note).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    view?.showSuccess()
                    view?.close()
                } else {
                    view?.showError("Update failed")
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
