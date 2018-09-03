package com.sudansh.carbooking

import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.entity.CabLiveResponse
import com.sudansh.carbooking.repository.entity.DropoffLocation
import com.sudansh.carbooking.repository.response.AvailabilityItemResponse
import com.sudansh.carbooking.repository.response.AvailabilityResponse


fun createCabLive(id: Int, lat: String, lng: String): CabLive {
    return CabLive(id, lat, true, lng)
}

fun createCabLiveResponse(lat: String, lng: String, count: Int = 10): CabLiveResponse {
    return CabLiveResponse(
            (1 until count).map {
                CabLive(it, lat + it, true, lng + it)
            })
}

fun createAvailability(id: Int, cars: Int, lat: Double, lng: Double) = Availability(id, cars, listOf(lat, lng))
fun createAvailabilityResponse(cars: Int, lat: Double, lng: Double, count: Int = 10) = AvailabilityResponse(
        (1 until count).map {
            AvailabilityItemResponse(it, cars, createDropListResponse(it, lat+it, lng+it), listOf(lat, lng))
        })

fun createDropListResponse(id: Int, lat: Double, lng:Double) =
    (1 until 10).map {
        DropoffLocation(id, listOf(lat, lng))
    }

