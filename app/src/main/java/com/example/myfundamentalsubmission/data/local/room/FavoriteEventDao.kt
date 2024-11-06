package com.example.myfundamentalsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity

@Dao
interface FavoriteEventDao {
    @Query("SELECT * FROM favorite_event")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>>

    @Query("SELECT * FROM favorite_event WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEventEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteEvent(favoriteEvent: FavoriteEventEntity)

    @Delete
    fun deleteFavoriteEvent(favoriteEvent: FavoriteEventEntity)
}