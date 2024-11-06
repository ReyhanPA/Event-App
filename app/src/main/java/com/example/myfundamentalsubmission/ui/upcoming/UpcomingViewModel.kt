package com.example.myfundamentalsubmission.ui.upcoming

import androidx.lifecycle.ViewModel
import com.example.myfundamentalsubmission.data.EventRepository

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllUpcomingEvents() = eventRepository.getAllUpcomingEvents()
}