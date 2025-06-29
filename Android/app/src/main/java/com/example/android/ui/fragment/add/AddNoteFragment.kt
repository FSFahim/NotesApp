package com.example.android.ui.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.data.repository.NotesRepository
import com.example.android.databinding.FragmentAddNoteBinding
import com.example.android.data.model.Note

class AddNoteFragment : Fragment(), AddNoteContract.View {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: AddNoteContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = AddNotePresenter(this, NotesRepository())

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(requireContext(), "Please enter title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(title = title, content = content)
            presenter.saveNote(note)
        }
    }

    override fun showSuccess() {
        Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }
}
