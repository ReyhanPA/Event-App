package com.example.myfundamentalsubmission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.myfundamentalsubmission.data.local.entity.EventFinishedEntity
import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity
import com.example.myfundamentalsubmission.data.local.room.EventFinishedDao
import com.example.myfundamentalsubmission.data.local.room.EventUpcomingDao
import com.example.myfundamentalsubmission.data.remote.response.EventResponse
import com.example.myfundamentalsubmission.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventUpcomingDao: EventUpcomingDao,
    private val eventFinishedDao: EventFinishedDao
) {
    fun getAllUpcomingEvents(): LiveData<Result<List<EventUpcomingEntity>>> = liveData {
        emit(Result.Loading)

        try {
            val response : EventResponse = apiService.getEvents("1")

            val eventList = response.listEvents.map { eventItem ->
                EventUpcomingEntity().apply {
                    id = eventItem.id ?: 0
                    name = eventItem.name ?: ""
                    imageLogo = eventItem.imageLogo
                    summary = eventItem.summary
                    mediaCover = eventItem.mediaCover
                    ownerName = eventItem.ownerName
                    beginTime = eventItem.beginTime
                    endTime = eventItem.endTime
                    quota = eventItem.quota
                    registrants = eventItem.registrants
                    description = eventItem.description
                }
            }

            withContext(Dispatchers.IO) {
                eventUpcomingDao.deleteAll()
                eventUpcomingDao.insertEvents(eventList)
            }

            emitSource(eventUpcomingDao.getEvents().map { Result.Success(it) })
        } catch (e: Exception) {
            emit(Result.Error("Error: ${e.message}"))
            emitSource(eventUpcomingDao.getEvents().map { Result.Success(it) })
        }
    }

    fun getAllFinishedEvents(active: String, keyword: String? = null): LiveData<Result<List<EventFinishedEntity>>> = liveData {
        emit(Result.Loading)

        try {
            val response: EventResponse = if (keyword != null) {
                apiService.getEvents(active, keyword)
            } else {
                apiService.getEvents(active)
            }

            val eventList = response.listEvents.map { eventItem ->
                EventFinishedEntity().apply {
                    id = eventItem.id ?: 0
                    name = eventItem.name ?: ""
                    imageLogo = eventItem.imageLogo
                    summary = eventItem.summary
                    mediaCover = eventItem.mediaCover
                    ownerName = eventItem.ownerName
                    beginTime = eventItem.beginTime
                    endTime = eventItem.endTime
                    quota = eventItem.quota
                    registrants = eventItem.registrants
                    description = eventItem.description
                }
            }

            withContext(Dispatchers.IO) {
                eventFinishedDao.deleteAll()
                eventFinishedDao.insertEvents(eventList)
            }

            emitSource(eventFinishedDao.getEvents().map { Result.Success(it) })
        } catch (e: Exception) {
            emit(Result.Error("Error: ${e.message}"))
            emitSource(eventFinishedDao.getEvents().map { Result.Success(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventUpcomingDao: EventUpcomingDao,
            eventFinishedDao: EventFinishedDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventUpcomingDao, eventFinishedDao)
            }.also { instance = it }
    }
}
