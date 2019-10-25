package com.insan.kucingpedia.DB;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {

    private SharedPreferences sharedPreferences;

    public SP(Context context) {
        sharedPreferences = context.getSharedPreferences("com.insan.kucingpedia.pref", Context.MODE_PRIVATE);
    }

    public void putInt(String key, int data) {
        sharedPreferences.edit().putInt(key, data).apply();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void putBoolean(String key, boolean data) {
        sharedPreferences.edit().putBoolean(key, data).apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
