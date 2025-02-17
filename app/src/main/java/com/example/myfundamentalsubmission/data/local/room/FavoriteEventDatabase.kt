package com.example.myfundamentalsubmission.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity

@Database(entities = [FavoriteEventEntity::class], version = 3)
abstract class FavoriteEventDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteEventDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteEventDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteEventDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteEventDatabase::class.java, "favorite_event")
                        .build()
                }
            }
            return INSTANCE as FavoriteEventDatabase
        }
    }
}