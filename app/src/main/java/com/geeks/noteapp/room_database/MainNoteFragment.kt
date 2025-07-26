package com.geeks.noteapp.room_database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.noteapp.R
import com.geeks.noteapp.databinding.FragmentMainNoteBinding

class MainNoteFragment : Fragment() {

    private lateinit var binding: FragmentMainNoteBinding
    private val noteAdapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = noteAdapter

        binding.fabGo.setOnClickListener {
            findNavController().navigate(R.id.detailFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val notes = App.appDatabase.noteDao().getAll()
        Log.d("MainNoteFragment", "Заметок ${notes.size}")
        noteAdapter.addNotes(notes)
    }

}
