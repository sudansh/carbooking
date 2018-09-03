package com.sudansh.carbooking.repository

import android.arch.lifecycle.LiveData
import com.sudansh.carbooking.data.ApiResponse
import com.sudansh.carbooking.data.AppExecutors
import com.sudansh.carbooking.data.NetworkBoundResource
import com.sudansh.carbooking.data.Resource
import com.sudansh.carbooking.repository.dao.AvailabilityDao
import com.sudansh.carbooking.repository.dao.CabLiveDao
import com.sudansh.carbooking.repository.dao.DropoffDao
import com.sudansh.carbooking.repository.dao.JoinDao
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.AvailableDropJoin
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.entity.CabLiveResponse
import com.sudansh.carbooking.repository.response.AvailabilityResponse
import com.sudansh.carbooking.testing.OpenForTesting
import com.sudansh.carbooking.util.orZero

@OpenForTesting
class CabRepository(
        val appExecutors: AppExecutors,
        val cabLiveDao: CabLiveDao,
        val availabilityDao: AvailabilityDao,
        val dropoffDao: DropoffDao,
        val joinDao: JoinDao,
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

    fun getAvailability(starttime: Long, endtime: Long, isFetch: Boolean = false): LiveData<Resource<List<Availability>>> {
        return object :
                NetworkBoundResource<List<Availability>, AvailabilityResponse>(appExecutors) {
            override fun saveCallResult(item: AvailabilityResponse) {
                if (item.data?.isNotEmpty() == true) insertResponseToDb(item)
            }

            override fun shouldFetch(data: List<Availability>?): Boolean =
                    isFetch || data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<Availability>> = availabilityDao.findAll()

            override fun createCall(): LiveData<ApiResponse<AvailabilityResponse>> =
                    apiService.getAvailability(starttime, endtime)
        }.asLiveData()
    }

    fun insertResponseToDb(item: AvailabilityResponse) {
        item.data?.forEach {
            //insert availability
            availabilityDao.insert(Availability(it.id, it.available_cars.orZero(), it.location.orEmpty()))

            //insert dropoff
            dropoffDao.insert(it.dropoff_locations.orEmpty())
            //insert join
            it.dropoff_locations?.forEach { dropoff ->
                joinDao.insert(AvailableDropJoin(dropoff.id, it.id))
            }
        }
    }
}