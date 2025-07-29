package com.geeks.noteApp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteModel ORDER BY id DESC")
    fun getAll(): List<NoteModel>

    @Insert
    fun insert(note: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)

    @Update
    fun updateNote(note: NoteModel)


}
