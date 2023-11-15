package com.example.myapplication.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.myapplication.utils.Constant

class SharedPrefs (context: Context){
    private val sharedPref = context.getSharedPreferences("account", MODE_PRIVATE)
    var isPremium: Boolean
        get() {
            return sharedPref.getBoolean(Constant.USERNAME, false)
        }
        set(value) {
            sharedPref.edit().putBoolean(Constant.USERNAME, value).apply()
        }

    var isEmail: String?
        get() {
            return sharedPref.getString(Constant.PASSWORD, "")
        }
        set(value) {
            sharedPref.edit().putString(Constant.PASSWORD, value).apply()
        }
}