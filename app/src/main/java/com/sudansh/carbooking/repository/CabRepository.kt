package com.sudansh.carbooking.repository

import android.arch.lifecycle.LiveData
import com.sudansh.carbooking.data.*
import com.sudansh.carbooking.repository.dao.CabLiveDao
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.entity.CabLiveResponse
import com.sudansh.carbooking.repository.response.AvailabilityResponse
import com.sudansh.carbooking.testing.OpenForTesting

@OpenForTesting
class CabRepository(
        val appExecutors: AppExecutors,
        val cabLiveDao: CabLiveDao,
        val apiService: ApiService
) {

    fun getCabLive(isFetch: Boolean = false): LiveData<Resource<List<CabLive>>> {
        return object :
                NetworkBoundResource<List<CabLive>, CabLiveResponse>(appExecutors) {
            override fun saveCallResult(item: CabLiveResponse) {
                cabLiveDao.insert(item.data)
            }

            override fun shouldFetch(data: List<CabLive>?): Boolean =
                    isFetch || data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<CabLive>> =
                    cabLiveDao.findAll()

            override fun createCall(): LiveData<ApiResponse<CabLiveResponse>> =
                    apiService.getCabLive()
        }.asLiveData()
    }

    fun getAvailability(starttime: Long, endtime: Long) =
            object : NetworkOnlyBoundResource<AvailabilityResponse>() {
                override fun createCall() = apiService.getAvailability(starttime, endtime)
            }.asLiveData()

}