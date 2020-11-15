package com.anoniscoding.paniclock.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

open class SharedPrefManager @Inject constructor(
    val gson: Gson,
    context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        APP_CACHE,
        Context.MODE_PRIVATE
    )
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()


    fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            sharedPreferencesEditor.remove(key).commit()
        }
    }

    fun savePref(key: String, value: Any?) {
        delete(key)

        when {
            value is Boolean -> sharedPreferencesEditor.putBoolean(key, (value as Boolean?)!!)
            value is Int -> sharedPreferencesEditor.putInt(key, (value as Int?)!!)
            value is Float -> sharedPreferencesEditor.putFloat(key, (value as Float?)!!)
            value is Long -> sharedPreferencesEditor.putLong(key, (value as Long?)!!)
            value is String -> sharedPreferencesEditor.putString(key, value as String?)
            value is Enum<*> -> sharedPreferencesEditor.putString(key, value.toString())
            value != null -> throw RuntimeException("Attempting to save non-primitive preference")
        }

        sharedPreferencesEditor.commit()
    }

    fun <T> saveObject(key: String, value: T) {
        val json = gson.toJson(value)
        savePref(key, json)
    }

    inline fun <reified T> getObject(key: String): T? {
        val json = getPref<String>(key)
        return gson.fromJson<Any>(json, T::class.java) as T?
    }

    inline fun <reified T> getMap(key: String): T? {
        val type = object : TypeToken<T>() {}.type

        val json = getPref(key, "")
        return gson.fromJson<Any>(json, type) as T?
    }

    fun <T> getPref(key: String): T {
        return sharedPreferences.all[key] as T
    }

    fun <T> getPref(key: String, defValue: T?): T? {
        val returnValue = sharedPreferences.all[key] as T
        return returnValue ?: defValue
    }

    fun <T> saveList(key: String, value: List<T>) {
        val stringValue = gson.toJson(value)
        savePref(key, stringValue)
    }

    inline fun <reified T> getList(key: String): List<T> {
        val returnedValue = getPref<String>(key)
        return returnedValue.let {
            gson.fromJson<List<T>>(it, object: TypeToken<List<T>>(){}.type)
        } ?: emptyList()
    }

    fun clearAll() {
        sharedPreferencesEditor.clear()
    }

    companion object {
        const val APP_CACHE = "balans"
    }
}