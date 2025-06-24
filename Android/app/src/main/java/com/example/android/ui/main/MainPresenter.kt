package com.example.android.ui.main

import com.example.android.model.Note
import com.example.android.data.NotesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(
    private var view: MainContract.View?,
    private val repository: NotesRepository
) : MainContract.Presenter {

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
