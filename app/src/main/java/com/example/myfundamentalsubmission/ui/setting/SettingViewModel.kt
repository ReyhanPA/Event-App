package com.example.myfundamentalsubmission.ui.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : ViewModel() {
    private val pref: SettingPreferences = SettingPreferences.getInstance(application)

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getDailyReminderSetting(): LiveData<Boolean> {
        return pref.getDailyReminderSetting().asLiveData()
    }

    fun saveDailyReminderSetting(isReminderActive: Boolean) {
        viewModelScope.launch {
            pref.saveDailyReminderSetting(isReminderActive)
        }
    }
}
