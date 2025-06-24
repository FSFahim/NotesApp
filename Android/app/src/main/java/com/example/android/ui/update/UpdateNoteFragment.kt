package com.example.android.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.data.NotesRepository
import com.example.android.databinding.FragmentUpdateNoteBinding
import com.example.android.model.Note

class UpdateNoteFragment : Fragment(), UpdateNoteContract.View {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: UpdateNoteContract.Presenter
    private var noteId = -1

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
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return
        }

        presenter = UpdateNotePresenter(this, NotesRepository())
        presenter.loadNote(noteId)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(requireContext(), "Changes Saved", Toast.LENGTH_SHORT).show()
        requireActivity().onBackPressedDispatcher.onBackPressed()
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
