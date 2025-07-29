package com.geeks.noteApp.room_database

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "data_base")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

}
