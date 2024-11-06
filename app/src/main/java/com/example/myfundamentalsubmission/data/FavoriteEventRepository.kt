package com.example.myfundamentalsubmission.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity
import com.example.myfundamentalsubmission.data.local.room.FavoriteEventDao
import com.example.myfundamentalsubmission.data.local.room.FavoriteEventDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteEventDatabase.getDatabase(application)
        mFavoriteEventDao = db.favoriteEventDao()
    }
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> = mFavoriteEventDao.getAllFavoriteEvents()
    fun getFavoriteEventById(id: String): LiveData<FavoriteEventEntity> = mFavoriteEventDao.getFavoriteEventById(id)
    fun insertFavoriteEvent(favoriteEvent: FavoriteEventEntity) {
        executorService.execute { mFavoriteEventDao.insertFavoriteEvent(favoriteEvent) }
    }
    fun deleteFavoriteEvent(favoriteEvent: FavoriteEventEntity) {
        executorService.execute { mFavoriteEventDao.deleteFavoriteEvent(favoriteEvent) }
    }
}