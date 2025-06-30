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
import com.example.android.ui.adapter.NoteItemListener
import com.example.android.ui.adapter.NotesAdapter
import com.example.android.ui.fragment.NoteListFragmentDirections
import com.example.android.ui.viewmodel.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment(), NoteItemListener {

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

        notesAdapter = NotesAdapter(listOf(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment()
            )
        }

        observeViewModel()
        viewModel.loadNotes()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.refreshData(notes)
        }

        viewModel.success.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
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
        note.id?.let { id ->
            viewModel.deleteNote(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}