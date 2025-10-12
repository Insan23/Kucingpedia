package com.insan.kucingpedia.DB

import android.content.Context
import android.content.SharedPreferences

class SP(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences("com.insan.kucingpedia.pref", Context.MODE_PRIVATE)
    }

    fun putInt(key: String?, data: Int) {
        sharedPreferences.edit().putInt(key, data).apply()
    }

    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun putBoolean(key: String?, data: Boolean) {
        sharedPreferences.edit().putBoolean(key, data).apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}
