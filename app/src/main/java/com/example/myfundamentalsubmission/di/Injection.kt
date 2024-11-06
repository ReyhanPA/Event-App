package com.example.myfundamentalsubmission.di

import android.content.Context
import com.example.myfundamentalsubmission.data.EventRepository
import com.example.myfundamentalsubmission.data.local.room.EventDatabase
import com.example.myfundamentalsubmission.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getDatabase(context)
        val eventUpcomingDao = database.eventUpcomingDao()
        val eventFinishedDao = database.eventFinishedDao()
        return EventRepository.getInstance(apiService, eventUpcomingDao, eventFinishedDao)
    }
}