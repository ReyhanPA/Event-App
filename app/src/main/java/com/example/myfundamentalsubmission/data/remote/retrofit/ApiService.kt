package com.example.myfundamentalsubmission.data.remote.retrofit

import com.example.myfundamentalsubmission.data.remote.response.DetailEventResponse
import com.example.myfundamentalsubmission.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: String,
        @Query("q") keyword: String? = null,
        @Query("limit") limit: String? = null
    ): EventResponse

    @GET("events/{id}")
    fun getEventById(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}