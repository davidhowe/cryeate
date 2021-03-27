package com.davidhowe.cryeate.repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import timber.log.Timber
import javax.inject.Inject

private const val PREF_FILE_NAME = "cryeate_prefs_v1"
private const val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"

class SharedPrefsRepo @Inject constructor(private val context: Context) {
    fun isFirstLaunch() : Boolean {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(firstLaunch : Boolean) {
        Timber.d("setFirstLaunch=$firstLaunch")
        context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).edit().putBoolean(KEY_FIRST_LAUNCH, firstLaunch).apply()
    }
}