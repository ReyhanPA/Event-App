package com.example.myfundamentalsubmission.ui.setting

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to define the DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey("theme_setting")
    private val reminderKey = booleanPreferencesKey("daily_reminder_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    fun getDailyReminderSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[reminderKey] ?: false
        }
    }

    suspend fun saveDailyReminderSetting(isReminderActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[reminderKey] = isReminderActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(application: Application): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(application.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
