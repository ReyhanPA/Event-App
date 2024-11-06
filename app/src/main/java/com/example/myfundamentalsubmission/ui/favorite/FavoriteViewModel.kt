package com.example.myfundamentalsubmission.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myfundamentalsubmission.data.FavoriteEventRepository
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> {
        return mFavoriteEventRepository.getAllFavoriteEvents()
    }
}