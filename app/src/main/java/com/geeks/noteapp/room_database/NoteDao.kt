package com.geeks.noteapp.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {
    @Insert
    fun insert(note: NoteModel)

    @Query("SELECT * FROM NoteModel ORDER BY id DESC")
    fun getAll(): List<NoteModel>
}
