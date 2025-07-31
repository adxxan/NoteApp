package com.geeks.noteApp.room_database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.geeks.noteApp.R
import com.geeks.noteApp.databinding.FragmentMainNoteBinding
import com.google.firebase.auth.FirebaseAuth


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

        binding.ivProfile.setOnClickListener {
            showUserDialog()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val photoUrl = currentUser.photoUrl
            if (photoUrl != null) {
                Glide.with(this)
                    .load(photoUrl)
                    .circleCrop()
                    .into(binding.ivProfile)
            } else {
                binding.ivProfile.setImageResource(R.drawable.ic_user_placeholder)
            }
        }

    }

    private fun showUserDialog() {
        val user = FirebaseAuth.getInstance().currentUser ?: return

        val dialogView = layoutInflater.inflate(R.layout.dialog_user_info, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val ivBigAvatar = dialogView.findViewById<ImageView>(R.id.ivBigAvatar)
        val tvName = dialogView.findViewById<TextView>(R.id.tvDialogName)
        val tvEmail = dialogView.findViewById<TextView>(R.id.tvDialogEmail)
        val btnLogout = dialogView.findViewById<Button>(R.id.btnDialogLogout)

        tvName.text = user.displayName ?: "Неизвестно"
        tvEmail.text = user.email ?: "Нет email"

        Glide.with(this)
            .load(user.photoUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_user_placeholder)
            .into(ivBigAvatar)

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            dialog.dismiss()
            findNavController().navigate(R.id.authFragment)
        }

        dialog.show()
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


    private fun onLongClick(note: NoteModel) {
        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Удалить заметку?")
        builder?.setPositiveButton("Да") { _, _ ->
            App.appDatabase.noteDao().deleteNote(note)
            onResume()
        }
        builder?.setNegativeButton("Нет", null)
        builder?.show()
    }

}
