package com.example.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.domain.model.Note
import com.example.android.databinding.FragmentUpdateNoteBinding
import com.example.android.ui.viewmodel.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UpdateNoteViewModel by viewModels()
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt("note_id", -1) ?: -1
        if (noteId == -1) {
            Toast.makeText(requireContext(), "Invalid Note ID", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        viewModel.loadNote(noteId)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedNote = Note(id = noteId, title = newTitle, content = newContent)
            viewModel.updateNote(updatedNote)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            binding.updateTitleEditText.setText(note.title)
            binding.updateContentEditText.setText(note.content)
        }

        viewModel.success.observe(viewLifecycleOwner) { updated ->
            if (updated) {
                Toast.makeText(requireContext(), "Changes Saved", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
