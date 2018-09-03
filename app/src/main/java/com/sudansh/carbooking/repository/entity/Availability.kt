package com.sudansh.carbooking.repository.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


@Entity
data class Availability(@ColumnInfo(name = "availableId") @PrimaryKey val availableId: Int,
                        val available_cars: Int,
                        @ColumnInfo(name = "available_location") val location: List<Double>) : ClusterItem {
    override fun getSnippet() = ""
    override fun getTitle() = if (available_cars > 1) String.format("%d cars available", available_cars) else String.format("%d car available", available_cars)
    override fun getPosition() = LatLng(location[0], location[1])
}

@Entity
data class DropoffLocation(
        @ColumnInfo(name = "dropId") @PrimaryKey val id: Int,
        @ColumnInfo(name = "drop_location") val location: List<Double>)

@Entity(primaryKeys = ["availableId", "dropId"],
        foreignKeys = [ForeignKey(
                entity = Availability::class,
                parentColumns = arrayOf("availableId"),
                childColumns = arrayOf("availableId")),
            ForeignKey(
                    entity = DropoffLocation::class,
                    parentColumns = arrayOf("dropId"),
                    childColumns = arrayOf("dropId"))])
data class AvailableDropJoin(
        val dropId: Int,
        val availableId: Int)