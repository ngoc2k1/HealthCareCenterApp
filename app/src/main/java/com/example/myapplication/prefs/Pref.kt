package com.example.myapplication.prefs
import android.content.Context
open class Pref {
    companion object {
        private const val FILE_NAME = "PREF"
        fun setString(context: Context, key: String, value: String) {
            val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            preferences.edit().putString(key, value).apply()
        }

        fun getString(context: Context, key: String, default: String): String {
            val preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            return preferences.getString(key, default).toString()
        }
    }
}


