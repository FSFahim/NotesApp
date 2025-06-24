package com.example.android.ui.update

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.data.NotesRepository
import com.example.android.databinding.ActivityUpdateNoteBinding
import com.example.android.model.Note

class UpdateNoteActivity : AppCompatActivity(), UpdateNoteContract.View {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var presenter: UpdateNoteContract.Presenter
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = UpdateNotePresenter(this, NotesRepository())

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            Toast.makeText(this, "Invalid Note ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        presenter.loadNote(noteId)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedNote = Note(id = noteId, title = newTitle, content = newContent)
            presenter.updateNote(updatedNote)
        }
    }

    override fun showNote(note: Note) {
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)
    }

    override fun showSuccess() {
        Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
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
