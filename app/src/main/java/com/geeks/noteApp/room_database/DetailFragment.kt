package com.geeks.noteApp.room_database

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.geeks.noteApp.R
import com.geeks.noteApp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var note: NoteModel? = null
    private var selectedColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        note = arguments?.getSerializable("note") as? NoteModel

        initData()

        val colors = listOf(
            R.color.colorYellow,
            R.color.colorPurple,
            R.color.colorPink,
            R.color.colorPink2,
            R.color.colorGreen,
            R.color.colorTurquoise
        )

        val palette = binding.colorPalette
        for (i in 0 until palette.childCount) {
            val colorView = palette.getChildAt(i)
            colorView.setOnClickListener {
                selectedColor = ContextCompat.getColor(requireContext(), colors[i])
                binding.root.setBackgroundColor(selectedColor!!)
            }
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val color = selectedColor ?: Color.BLACK

            if (note != null) {
                App.appDatabase?.noteDao()?.updateNote(
                    NoteModel(desc = desc, title = title, id = note!!.id, color = color)
                )
            } else {
                App.appDatabase.noteDao().insert(
                    NoteModel(title = title, desc = desc, color = color)
                )
            }

            findNavController().navigateUp()
        }

        binding.btnDelete.setOnClickListener {
            note?.let {
                App.appDatabase.noteDao().deleteNote(it)
            }
            findNavController().navigateUp()
        }

        binding.btnMenu.setOnClickListener {
            binding.optionsMenu.visibility =
                if (binding.optionsMenu.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun initData() {
        note?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextDesc.setText(it.desc)
            binding.root.setBackgroundColor(it.color)
            selectedColor = it.color
        }
    }
}
