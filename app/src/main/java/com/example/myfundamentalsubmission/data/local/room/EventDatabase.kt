package com.example.myfundamentalsubmission.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfundamentalsubmission.data.local.entity.EventFinishedEntity
import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity

@Database(entities = [EventUpcomingEntity::class, EventFinishedEntity::class], version = 2)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventUpcomingDao(): EventUpcomingDao
    abstract fun eventFinishedDao(): EventFinishedDao
    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): EventDatabase {
            if (INSTANCE == null) {
                synchronized(EventDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EventDatabase::class.java, "event")
                        .build()
                }
            }
            return INSTANCE as EventDatabase
        }
    }
}