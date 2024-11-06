package com.example.myfundamentalsubmission.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfundamentalsubmission.data.FavoriteEventRepository
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity
import com.example.myfundamentalsubmission.data.remote.response.DetailEvent
import com.example.myfundamentalsubmission.data.remote.response.DetailEventResponse
import com.example.myfundamentalsubmission.data.remote.retrofit.ApiConfig
import com.example.myfundamentalsubmission.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application) : ViewModel() {
    private val _event = MutableLiveData<DetailEvent>()
    val event: LiveData<DetailEvent> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    companion object{
        private const val TAG = "DetailActivityViewModel"
    }

    fun findEventById(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEventById(id.toString())
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val detailEventResponse = response.body()
                    if (detailEventResponse != null && !detailEventResponse.error) {
                        _event.value = detailEventResponse.event
                    } else {
                        _errorMessage.value = Event("Error: ${detailEventResponse?.message ?: "Unknown error"}")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMessage.value = Event("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _errorMessage.value = Event("Error: ${t.message}")
            }
        })
    }
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)
    fun getFavoriteEventById(id: String): LiveData<FavoriteEventEntity> = mFavoriteEventRepository.getFavoriteEventById(id)
    fun insertFavoriteEvent(favoriteEvent: FavoriteEventEntity) {
        viewModelScope.launch {
            mFavoriteEventRepository.insertFavoriteEvent(favoriteEvent)
        }
    }
    fun deleteFavoriteEvent(favoriteEvent: FavoriteEventEntity) {
        viewModelScope.launch {
            mFavoriteEventRepository.deleteFavoriteEvent(favoriteEvent)
        }
    }
}