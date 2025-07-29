package com.geeks.noteApp.room_database

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private val pref = context.getSharedPreferences("key_pref", MODE_PRIVATE)

    fun saveText(value: String) {
        pref.edit().putString(KEY_OF_NANE, value).apply()
    }

    fun getText(): String? {
        return pref.getString(KEY_OF_NANE, "DEFAULT VALUE")
    }

    fun saveCount(value: Int) {
        pref.edit().putInt(KEY_OF_COUNTER, value).apply()
    }

    fun getCount(): Int {
        return pref.getInt(KEY_OF_COUNTER, 0)
    }

    companion object {
        const val KEY_OF_NANE: String = "key_of_name"
        const val KEY_OF_COUNTER: String = "key_of_counter"
    }
}