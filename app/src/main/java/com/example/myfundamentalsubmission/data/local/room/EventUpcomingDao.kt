package com.example.myfundamentalsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity

@Dao
interface EventUpcomingDao {
    @Query("SELECT * FROM event_upcoming")
    fun getEvents(): LiveData<List<EventUpcomingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventUpcomingEntity>)

    @Query("DELETE FROM event_upcoming")
    suspend fun deleteAll()
}