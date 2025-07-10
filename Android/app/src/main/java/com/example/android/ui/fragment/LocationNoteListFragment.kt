package com.example.android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.FragmentLocationNoteListBinding
import com.example.android.utils.LocationService
import com.example.android.ui.adapter.LocationNotesAdapter
import com.example.android.ui.adapter.LocationNotesItemListener
import com.example.android.ui.viewmodel.LocationNoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationNoteListFragment : Fragment(), LocationNotesItemListener {

    private var _binding: FragmentLocationNoteListBinding? = null
    private val binding get() = _binding!!

    private val locationNotesViewModel: LocationNoteListViewModel by viewModels()
    private lateinit var locationNotesAdapter: LocationNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.fetchLocationButton.setOnClickListener {
                startLocationService()
        }

        binding.uploadNotesButton.setOnClickListener {
            locationNotesViewModel.uploadAndClearLocationNotes()
        }
    }

    private fun setupRecyclerView() {
        locationNotesAdapter = LocationNotesAdapter(this)
        binding.locationNotesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.locationNotesRecyclerView.adapter = locationNotesAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            locationNotesViewModel.locationNotesFlow.collectLatest { notes ->
                locationNotesAdapter.submitList(notes)
            }
        }
    }

    private fun startLocationService() {
        val intent = Intent(requireContext(), LocationService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
