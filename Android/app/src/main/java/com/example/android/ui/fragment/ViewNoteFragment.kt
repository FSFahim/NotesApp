package com.example.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.databinding.FragmentViewNoteBinding
import com.example.android.ui.viewmodel.ViewNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewNoteFragment : Fragment() {

    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewNoteViewModel by viewModels()
    private var noteId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
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

        observeViewModel()
        viewModel.loadNote(noteId)
    }

    private fun observeViewModel() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            binding.viewTitleTextView.text = note.title
            binding.viewContentTextView.text = note.content
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
