package com.sudansh.carbooking.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.sudansh.carbooking.ApiUtil
import com.sudansh.carbooking.createAvailabilityResponse
import com.sudansh.carbooking.createCabLiveResponse
import com.sudansh.carbooking.data.Resource
import com.sudansh.carbooking.repository.dao.CabLiveDao
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.response.AvailabilityResponse
import com.sudansh.carbooking.util.InstantAppExecutors
import com.sudansh.carbooking.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class CabRepositoryTest {
    private val cabLiveDao = mock(CabLiveDao::class.java)
    private val api = mock(ApiService::class.java)

    private val repo = CabRepository(InstantAppExecutors(), cabLiveDao, api)
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testLiveData() {
        val dbData = MutableLiveData<List<CabLive>>()
        `when`(cabLiveDao.findAll()).thenReturn(dbData)

        val call = ApiUtil.successCall(createCabLiveResponse("123.0", "456.0", 10))
        `when`(api.getCabLive()).thenReturn(call)
        val observer = mock<Observer<Resource<List<CabLive>>>>()

        //fetch data from db
        repo.getCabLive(false).observeForever(observer)

        //verify no apiService is called
        verify(api, never()).getCabLive()

        val updatedDbData = MutableLiveData<List<CabLive>>()
        `when`(cabLiveDao.findAll()).thenReturn(updatedDbData)
        dbData.value = null

        //force fetch from apiService
        repo.getCabLive(true).observeForever(observer)

        //verify apiService is called
        verify(api).getCabLive()
    }

    @Test
    fun testAvailability() {
        val call = ApiUtil.successCall(createAvailabilityResponse(5, 123.0, 456.0, 10))
        `when`(api.getAvailability(anyLong(), anyLong())).thenReturn(call)
        val observer = mock<Observer<Resource<AvailabilityResponse>>>()

        //force fetch from apiService
        repo.getAvailability(100, 200).observeForever(observer)

        //verify apiService is called
        verify(api).getAvailability(100, 200)
    }

}