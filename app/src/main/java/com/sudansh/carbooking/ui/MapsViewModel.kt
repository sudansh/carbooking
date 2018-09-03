package com.sudansh.carbooking.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sudansh.carbooking.data.Resource
import com.sudansh.carbooking.repository.CabRepository
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.testing.OpenForTesting
import com.sudansh.carbooking.util.minSlots
import com.sudansh.carbooking.util.switch
import java.util.*
import java.util.concurrent.TimeUnit

@OpenForTesting
class MapsViewModel(private val repo: CabRepository) : ViewModel() {
    private val refresh: MutableLiveData<Boolean> = MutableLiveData()
    var hideOnTrip: Boolean = false
    private var timePair: MutableLiveData<Pair<Long, Long>> = MutableLiveData()
    val firstTabActive = MutableLiveData<Boolean>()
    val cabLive: LiveData<Resource<List<CabLive>>> = refresh.switch { isFetch ->
        repo.getCabLive(isFetch)
    }
    val availability: LiveData<Resource<List<Availability>>> = timePair.switch { times ->
        repo.getAvailability(times.first, times.second)
    }

    init {
        firstTabActive.value = true
    }

    fun getLive(isFetch: Boolean) {
        refresh.value = isFetch
    }

    fun getAvailability(starttime: Long, endtime: Long) {
        timePair.value = Pair(starttime, endtime)
    }

    /**
     * Gets the availability based on chosed time slot from [minSlots]
     */
    fun getAvailabilityFromSlot(position: Int) {
        val oneHourFromNow = Date().apply { Date(this.time + TimeUnit.MINUTES.toMillis(60)) }
        val end = Date(oneHourFromNow.time + TimeUnit.MINUTES.toMillis(minSlots[position].first))
        getAvailability(oneHourFromNow.time/1000, end.time/1000)
    }

    fun setFirstTabActive(b: Boolean) {
        firstTabActive.value = b
    }

    fun hideOnTrip(checked: Boolean) {
        hideOnTrip = checked
        getLive(false)
    }
}