package com.example.android.ui.notelist

import com.example.android.data.NotesRepository
import com.example.android.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListPresenter(
    private var view: NoteListContract.View?,
    private val repository: NotesRepository
) : NoteListContract.Presenter {

    override fun deleteNote(note: Note) {
        note.id?.let { id ->
            repository.deleteNoteById(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        view?.showSuccess()
                        loadNotes()
                    } else {
                        view?.showError("Delete failed")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    view?.showError("Error: ${t.localizedMessage}")
                }
            })
        }
    }

    override fun loadNotes() {
        repository.getAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    view?.showNotes(response.body() ?: emptyList())
                } else {
                    view?.showError("Failed to load notes")
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                view?.showError("Error: ${t.localizedMessage}")
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}