package com.example.android.ui.fragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.android.data.repository.NotesRepository
import com.example.android.databinding.FragmentViewNoteBinding
import com.example.android.data.model.Note

class ViewNoteFragment : Fragment(), ViewNoteContract.View {

    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ViewNoteContract.Presenter
    private val args: ViewNoteFragmentArgs by navArgs()
    private val noteId : Int get() = args.noteId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
