package com.example.android.ui.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.data.NotesRepository
import com.example.android.databinding.ActivityAddNoteBinding
import com.example.android.model.Note

class AddNoteActivity : AppCompatActivity(), AddNoteContract.View {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var presenter: AddNoteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AddNotePresenter(this, NotesRepository())

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(title = title, content = content)
            presenter.saveNote(note)
        }
    }

    override fun showSuccess() {
        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
