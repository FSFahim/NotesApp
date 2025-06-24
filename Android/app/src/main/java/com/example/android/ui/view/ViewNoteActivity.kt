package com.example.android.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.data.NotesRepository
import com.example.android.databinding.ActivityViewNoteBinding
import com.example.android.model.Note

class ViewNoteActivity : AppCompatActivity(), ViewNoteContract.View {

    private lateinit var binding: ActivityViewNoteBinding
    private lateinit var presenter: ViewNoteContract.Presenter
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            Toast.makeText(this, "Invalid Note Id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        presenter = ViewNotePresenter(this, NotesRepository())
        presenter.loadNote(noteId)
    }

    override fun showNote(note: Note) {
        binding.viewTitleTextView.text = note.title
        binding.viewContentTextView.text = note.content
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
