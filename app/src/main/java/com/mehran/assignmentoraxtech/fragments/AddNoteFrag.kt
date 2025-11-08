package com.mehran.assignmentoraxtech.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mehran.assignmentoraxtech.databinding.FragmentAddNoteBinding
import com.mehran.assignmentoraxtech.models.MyDBHelper

class AddNoteFrag : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var dbHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        dbHelper = MyDBHelper(requireContext())

        binding.btnAddNote.setOnClickListener {
            val title = binding.etNoteTitle.text.toString().trim()
            val desc = binding.etNoteDescription.text.toString().trim()

            if (title.isBlank()) {
                binding.etNoteTitle.error = "Title must not be empty!"
                return@setOnClickListener
            }

            if (desc.isBlank()) {
                binding.etNoteDescription.error = "You must add some description!"
                return@setOnClickListener
            }

            dbHelper.addNote(title, desc)
            binding.etNoteTitle.text?.clear()
            binding.etNoteDescription.text?.clear()

            parentFragmentManager.setFragmentResult("refresh_notes", Bundle())
            Toast.makeText(requireContext(), "Note added successfully!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}