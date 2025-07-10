package com.example.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.data.model.Note
import com.example.android.databinding.FragmentNoteListBinding
import com.example.android.ui.adapter.NotesItemListener
import com.example.android.ui.adapter.NotesAdapter
import com.example.android.ui.viewmodel.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment(), NotesItemListener {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NoteListViewModel by viewModels()
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

        notesAdapter = NotesAdapter(this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                NotesListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            )
        }

        binding.locationNotesButton.setOnClickListener {
            findNavController().navigate(
                NotesListFragmentDirections.actionNoteListFragmentToLocationNoteListFragment()
            )
        }

        observeViewModel()
        viewModel.loadNotes()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.submitList(notes)
        }

        viewModel.success.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNoteClicked(note: Note) {
        val action = NotesListFragmentDirections
            .actionNoteListFragmentToViewNoteFragment(note.id!!)
        findNavController().navigate(action)
    }

    override fun onNoteEditRequested(note: Note) {
        val action = NotesListFragmentDirections
            .actionNoteListFragmentToUpdateNoteFragment(note.id!!)
        findNavController().navigate(action)
    }

    override fun onNoteDeleteRequested(note: Note) {
        note.id?.let { id ->
            viewModel.deleteNote(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}