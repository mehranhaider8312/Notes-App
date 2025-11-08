package com.mehran.assignmentoraxtech.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehran.assignmentoraxtech.adapters.NotesAdapter
import com.mehran.assignmentoraxtech.databinding.FragmentViewNotesBinding
import com.mehran.assignmentoraxtech.models.MyDBHelper
import com.mehran.assignmentoraxtech.models.Note

class ViewNotesFrag : Fragment() {

    private lateinit var binding: FragmentViewNotesBinding
    private lateinit var dbHelper: MyDBHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = MyDBHelper(requireContext())
        parentFragmentManager.setFragmentResultListener("refresh_notes", this) { requestKey, bundle ->
            refreshNotes()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewNotesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val notes = dbHelper.fetchNotes()
        updateUIVisibility(notes.isEmpty())
        Log.d("ViewNotesFrag", "Fetched ${notes.size} notes from database")

        notesAdapter = NotesAdapter(ArrayList(notes))
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
            setHasFixedSize(true)
        }
    }

    fun refreshNotes() {
        if (!isAdded || context == null) {
            return
        }

        if (!::dbHelper.isInitialized) {
            dbHelper = MyDBHelper(requireContext())
        }

        val notes = dbHelper.fetchNotes()
        updateUIVisibility(notes.isEmpty())
        Log.d("ViewNotesFrag", "Refreshed - Fetched ${notes.size} notes from database")

        if (::notesAdapter.isInitialized) {
            notesAdapter.updateNotes(notes)
        } else {
            setupRecyclerView()
        }
    }

    private fun updateUIVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            binding.animation.visibility = View.VISIBLE
            binding.rvNotes.visibility = View.GONE
        } else {
            binding.animation.visibility = View.GONE
            binding.rvNotes.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        refreshNotes()
    }
}