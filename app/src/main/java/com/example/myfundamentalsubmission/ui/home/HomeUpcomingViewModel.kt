package com.example.myfundamentalsubmission.ui.home

import androidx.lifecycle.ViewModel
import com.example.myfundamentalsubmission.data.EventRepository

class HomeUpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllUpcomingEvents() = eventRepository.getAllUpcomingEvents()
}