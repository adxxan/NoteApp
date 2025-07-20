package com.geeks.noteapp

import android.content.Context
import android.content.SharedPreferences

object PrefHelper {
    private const val PREF_NAME = "note_app_pref"
    private const val KEY_ONBOARD_SHOWN = "onboard_shown"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isOnBoardShown(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_ONBOARD_SHOWN, false)
    }

    fun setOnBoardShown(context: Context) {
        getPrefs(context).edit().putBoolean(KEY_ONBOARD_SHOWN, true).apply()
    }
}
