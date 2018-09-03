package com.sudansh.carbooking.repository.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.sudansh.carbooking.util.coordinate

class CabLiveResponse(val data: List<CabLive>)

@Entity
class CabLive(
        @PrimaryKey var id: Int,
        var latitude: String?,
        var is_on_trip: Boolean,
        var longitude: String?) : ClusterItem {
    override fun getSnippet() = ""

    override fun getTitle() = ""

    override fun getPosition() = LatLng(latitude.coordinate(), longitude.coordinate())
}