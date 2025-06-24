package com.example.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.ui.add.AddNoteActivity
import com.example.android.ui.adapter.NotesAdapter
import com.example.android.databinding.ActivityMainBinding
import com.example.android.data.NotesRepository
import com.example.android.model.Note
import com.example.android.ui.adapter.NoteItemListener
import com.example.android.ui.update.UpdateNoteActivity
import com.example.android.ui.view.ViewNoteActivity

class MainActivity : AppCompatActivity(), MainContract.View, NoteItemListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, NotesRepository())

        notesAdapter = NotesAdapter(listOf(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        presenter.loadNotes()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showNotes(notes: List<Note>) {
        notesAdapter.refreshData(notes)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClicked(note: Note) {
        val intent = Intent(this, ViewNoteActivity::class.java)
        intent.putExtra("note_id", note.id)
        startActivity(intent)
    }

    override fun onNoteEditRequested(note: Note) {
        val intent = Intent(this, UpdateNoteActivity::class.java)
        intent.putExtra("note_id", note.id)
        startActivity(intent)
    }

    override fun onNoteDeleteRequested(note: Note) {
        presenter.deleteNote(note)
    }

}
