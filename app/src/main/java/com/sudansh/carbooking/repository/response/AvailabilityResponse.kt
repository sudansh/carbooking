package com.sudansh.carbooking.repository.response

import com.sudansh.carbooking.repository.entity.DropoffLocation

class AvailabilityResponse(val data: List<AvailabilityItemResponse>?)

class AvailabilityItemResponse(val id: Int,
                               val available_cars: Int?,
                               val dropoff_locations: List<DropoffLocation>?,
                               val location: List<Double>?)
