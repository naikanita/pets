package com.learning.pets

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.learning.pets.bl.CommonUtil.PET_PREFERENCES

/**
 * @author Anita
 * The Application class is the base class for this application
 */
class PetApplication : Application() {
    companion object {
        private var instance: Application? = null
        private var sharedPreferences: SharedPreferences? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getSharedPreference(): SharedPreferences? {
            return sharedPreferences
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences =
            this.getSharedPreferences(getPackageName() + PET_PREFERENCES, Context.MODE_PRIVATE)
    }

}