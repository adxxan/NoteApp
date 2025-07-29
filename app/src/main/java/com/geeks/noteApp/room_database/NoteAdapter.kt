package com.geeks.noteApp.room_database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.noteApp.databinding.ItemListNoteBinding

class NoteAdapter(private val onLongClick: (note: NoteModel) -> Unit, private val onClick: (note: NoteModel) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val notes = mutableListOf<NoteModel>()

    fun addNotes(newNotes: List<NoteModel>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemListNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(private val binding: ItemListNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel) {
            binding.textTitle.text = note.title
            binding.textDesc.text = note.desc

            binding.cardView.setCardBackgroundColor(note.color)

            itemView.setOnLongClickListener{
                onLongClick(note)
                true

            }

            itemView.setOnClickListener{
                onClick(note)

            }
        }

    }
}

