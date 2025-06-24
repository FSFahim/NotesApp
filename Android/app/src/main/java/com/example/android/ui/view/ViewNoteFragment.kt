package com.example.android.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.data.NotesRepository
import com.example.android.databinding.FragmentViewNoteBinding
import com.example.android.model.Note

class ViewNoteFragment : Fragment(), ViewNoteContract.View {

    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ViewNoteContract.Presenter
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
            Toast.makeText(requireContext(), "Invalid Note Id", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }
}
