package com.example.android.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.R
import com.example.android.data.NotesRepository
import com.example.android.databinding.FragmentNoteListBinding
import com.example.android.model.Note
import com.example.android.ui.adapter.NoteItemListener
import com.example.android.ui.adapter.NotesAdapter
import com.example.android.ui.add.AddNoteFragment
import com.example.android.ui.update.UpdateNoteFragment
import com.example.android.ui.view.ViewNoteFragment

class NoteListFragment : Fragment(), NoteListContract.View, NoteItemListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: NoteListContract.Presenter
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = NoteListPresenter(this, NotesRepository())

        notesAdapter = NotesAdapter(listOf(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AddNoteFragment())
                .addToBackStack(null)
                .commit()
        }

        presenter.loadNotes()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

    override fun showNotes(notes: List<Note>) {
        notesAdapter.refreshData(notes)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClicked(note: Note) {
        val fragment = ViewNoteFragment().apply {
            arguments = Bundle().apply {
                putInt("note_id", note.id ?: -1)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNoteEditRequested(note: Note) {
        val fragment = UpdateNoteFragment().apply {
            arguments = Bundle().apply {
                putInt("note_id", note.id ?: -1)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNoteDeleteRequested(note: Note) {
        presenter.deleteNote(note)
    }
}
