package com.example.myfundamentalsubmission.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfundamentalsubmission.data.EventRepository
import com.example.myfundamentalsubmission.di.Injection
import com.example.myfundamentalsubmission.ui.finished.FinishedViewModel
import com.example.myfundamentalsubmission.ui.home.HomeFinishedViewModel
import com.example.myfundamentalsubmission.ui.home.HomeUpcomingViewModel
import com.example.myfundamentalsubmission.ui.upcoming.UpcomingViewModel

class EventViewModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(HomeUpcomingViewModel::class.java)) {
            return HomeUpcomingViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(HomeFinishedViewModel::class.java)) {
            return HomeFinishedViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: EventViewModelFactory? = null
        fun getInstance(context: Context): EventViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: EventViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}