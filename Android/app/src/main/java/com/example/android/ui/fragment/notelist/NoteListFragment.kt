package com.example.android.ui.fragment.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.data.repositroy.NotesRepository
import com.example.android.databinding.FragmentNoteListBinding
import com.example.android.data.model.Note
import com.example.android.ui.adapter.NoteItemListener
import com.example.android.ui.adapter.NotesAdapter

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
            findNavController().navigate(
                NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            )
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
        val action = NoteListFragmentDirections
            .actionNoteListFragmentToViewNoteFragment(note.id!!)
        findNavController().navigate(action)
    }

    override fun onNoteEditRequested(note: Note) {
        val action = NoteListFragmentDirections
            .actionNoteListFragmentToUpdateNoteFragment(note.id!!)
        findNavController().navigate(action)
    }

    override fun onNoteDeleteRequested(note: Note) {
        presenter.deleteNote(note)
    }

    override fun showSuccess() {
        Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }
}
