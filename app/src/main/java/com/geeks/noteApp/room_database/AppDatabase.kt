package com.geeks.noteApp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
