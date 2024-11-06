package com.example.myfundamentalsubmission.ui.home

import androidx.lifecycle.ViewModel
import com.example.myfundamentalsubmission.data.EventRepository

class HomeFinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllFinishedEvents() = eventRepository.getAllFinishedEvents("0")
}