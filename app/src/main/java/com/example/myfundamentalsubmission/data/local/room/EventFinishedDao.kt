package com.example.myfundamentalsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfundamentalsubmission.data.local.entity.EventFinishedEntity

@Dao
interface EventFinishedDao {
    @Query("SELECT * FROM event_finished")
    fun getEvents(): LiveData<List<EventFinishedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventFinishedEntity>)

    @Query("DELETE FROM event_finished")
    suspend fun deleteAll()
}