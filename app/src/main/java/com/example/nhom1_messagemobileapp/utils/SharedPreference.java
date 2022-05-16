package com.example.nhom1_messagemobileapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static SharedPreference instance;
    private SharedPreferences sharedPreferences;

    private SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("DATA",Context.MODE_PRIVATE);
    }

    public synchronized static SharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreference(context);
        }
        return instance;
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void saveData(String key,Boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public String getData(String key, String defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, defValue);
        }
        return defValue;
    }

    public Boolean getData(String key, boolean defValue) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(key, defValue);
        }
        return defValue;
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

}
