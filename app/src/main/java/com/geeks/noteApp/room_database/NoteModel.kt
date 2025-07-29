package com.geeks.noteApp.room_database

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val color: Int = Color.BLACK
):Serializable
