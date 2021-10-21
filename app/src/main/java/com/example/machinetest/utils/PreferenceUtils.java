package com.example.machinetest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * @author Rishad
 * @since 21/10/2021
 */

public class PreferenceUtils {

    private static final boolean DEFAULT_BOOLEAN = false;
    private static SharedPreferences sInstance;

    public static void newInstance(Context context) {
        sInstance = PreferenceManager.getDefaultSharedPreferences(context);
    }


    private static boolean getBoolean(SharedPreferences instance, String key, boolean defaultValue) {
        return sInstance.getBoolean(key, defaultValue);
    }

    private static void setBoolean(SharedPreferences instance, String key, boolean value) {
        Editor editor = sInstance.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static String getString(SharedPreferences instance, String key, String defaultValue) {
        return instance.getString(key, defaultValue);
    }

    private static void setString(SharedPreferences instance, String key, String value) {
        Editor editor = instance.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static int getInt(SharedPreferences instance, String key, int defaultValue) {
        return instance.getInt(key, defaultValue);
    }

    private static void setInt(SharedPreferences instance, String key, int value) {
        Editor editor = instance.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static boolean clear() {
        try {
            sInstance.edit().clear().apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* -------------------------------------------------------------------------------------------*/

    public static boolean isDarkMode(){
        return getBoolean(sInstance, Keys.IS_DARK_MODE, DEFAULT_BOOLEAN);
    }
    public static void setAsDarkMode(boolean value){
        setBoolean(sInstance, Keys.IS_DARK_MODE, value);
    }

    private static class Keys {
        private static final String IS_DARK_MODE = "is_dark_mode";
    }

}
