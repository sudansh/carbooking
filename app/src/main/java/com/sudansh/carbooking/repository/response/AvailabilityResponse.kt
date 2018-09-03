package com.sudansh.carbooking.repository.response

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class AvailabilityResponse(val data: List<Availability>?)

class Availability(val id: Int,
                   private val available_cars: Int,
                   val location: List<Double>) : ClusterItem {
    override fun getSnippet() = ""
    override fun getTitle() = if (available_cars > 1) String.format("%d cars available", available_cars) else String.format("%d car available", available_cars)
    override fun getPosition() = LatLng(location[0], location[1])

}