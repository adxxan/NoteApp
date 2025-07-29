package com.geeks.noteApp.room_database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.noteApp.R
import com.geeks.noteApp.databinding.FragmentMainNoteBinding


class MainNoteFragment : Fragment() {

    private lateinit var binding: FragmentMainNoteBinding
    private val noteAdapter = NoteAdapter(::onLongClick, ::onClick)

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

    private fun onClick(note: NoteModel) {
        val bundle = Bundle().apply {
            putSerializable("note", note)
        }
        findNavController().navigate(R.id.detailFragment, bundle)
    }


    private fun onLongClick(note: NoteModel){
        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Удалить ?")
        builder?.setPositiveButton("Да") { dialog, id ->
            App.appDatabase.noteDao().deleteNote(note)
            onResume()

        }
        builder?.setNegativeButton("Нет") { dialog, id ->

        }

        builder?.show()


        App.appDatabase.noteDao().deleteNote(note)
    }
}
