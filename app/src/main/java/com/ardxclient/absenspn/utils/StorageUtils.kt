package com.ardxclient.absenspn.utils

import android.content.Context
import android.content.SharedPreferences

object StorageUtils {
    private const val PREFS_NAME = "ABSEN_SPN_STORAGE"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(context: Context, key: String, value: String) {
        val editor = getPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String, defaultValue: String? = null): String? {
        return getPreferences(context).getString(key, defaultValue)
    }

    fun saveInt(context: Context, key: String, value: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        return getPreferences(context).getInt(key, defaultValue)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getPreferences(context).getBoolean(key, defaultValue)
    }

    fun saveFloat(context: Context, key: String, value: Float) {
        val editor = getPreferences(context).edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(context: Context, key: String, defaultValue: Float = 0f): Float {
        return getPreferences(context).getFloat(key, defaultValue)
    }

    fun saveLong(context: Context, key: String, value: Long) {
        val editor = getPreferences(context).edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(context: Context, key: String, defaultValue: Long = 0L): Long {
        return getPreferences(context).getLong(key, defaultValue)
    }

    fun saveStringSet(context: Context, key: String, values: Set<String>) {
        val editor = getPreferences(context).edit()
        editor.putStringSet(key, values)
        editor.apply()
    }

    fun getStringSet(context: Context, key: String, defaultValues: Set<String>? = null): Set<String>? {
        return getPreferences(context).getStringSet(key, defaultValues)
    }

    fun remove(context: Context, key: String) {
        val editor = getPreferences(context).edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}