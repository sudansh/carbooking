package com.sudansh.carbooking.repository

import android.arch.lifecycle.LiveData
import com.sudansh.carbooking.data.ApiResponse
import com.sudansh.carbooking.repository.entity.CabLiveResponse
import com.sudansh.carbooking.repository.response.AvailabilityResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("locations")
    fun getCabLive(): LiveData<ApiResponse<CabLiveResponse>>

    @GET("availability")
    fun getAvailability(@Query("startTime") starttime: Long, @Query("endTime") endtime: Long)
            : LiveData<ApiResponse<AvailabilityResponse>>

}