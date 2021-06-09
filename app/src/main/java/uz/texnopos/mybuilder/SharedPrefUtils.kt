package uz.texnopos.mybuilder

import android.content.Context
import android.content.SharedPreferences
import uz.texnopos.mybuilder.Constants.myPreferences
import uz.texnopos.mybuilder.MyBuilderApp.Companion.getAppInstance

class SharedPrefUtils {
    private val mSharedPreferences: SharedPreferences = getAppInstance()
        .getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
    private var mSharedPreferencesEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    init {
        mSharedPreferencesEditor.apply()
    }

    fun setValue(key: String, value: Any?) {
        when (value) {
            is Int? -> {
                mSharedPreferencesEditor.putInt(key, value!!).apply()
            }
            is Float? -> {
                mSharedPreferencesEditor.putFloat(key, value!!).apply()
            }
            is String? -> {
                mSharedPreferencesEditor.putString(key, value!!).apply()
            }
            is Long? -> {
                mSharedPreferencesEditor.putLong(key, value!!).apply()
            }
            is Boolean? -> {
                mSharedPreferencesEditor.putBoolean(key, value!!).apply()
            }
        }
    }

    fun getStringValue(key: String, defaultValue: String = ""): String {
        return mSharedPreferences.getString(key, defaultValue)!!
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    fun getBooleanValue(keyFlag: String, defaultValue: Boolean = false): Boolean {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    fun removeKey(key: String) {
        mSharedPreferencesEditor.remove(key).apply()
    }

    fun clear() {
        mSharedPreferencesEditor.clear().apply()
    }
}