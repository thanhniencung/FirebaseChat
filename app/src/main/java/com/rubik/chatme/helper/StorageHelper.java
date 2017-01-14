package com.rubik.chatme.helper;

import android.content.SharedPreferences;

import com.rubik.chatme.ChatMeApplication;

/**
 * Created by kiennguyen on 1/2/17.
 */

@SuppressWarnings("unused")
public class StorageHelper {
    private final static String PREF_FILE = "PREF";

    /**
     * Set a string shared preference
     * testing function
     * 
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void cacheString(String key, String value) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Set a integer shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void cacheInt(String key, int value) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set a Boolean shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void cacheBoolean(String key, boolean value) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static String getString(String key, String defValue) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences settings = ChatMeApplication.getContext().getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }
}
