package com.example.myfundamentalsubmission.ui.finished

import androidx.lifecycle.ViewModel
import com.example.myfundamentalsubmission.data.EventRepository

class FinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getAllFinishedEvents() = eventRepository.getAllFinishedEvents("0")
    fun searchFinishedEvents(keyword: String) = eventRepository.getAllFinishedEvents("-1", keyword)
}