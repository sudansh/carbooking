package com.sudansh.carbooking.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sudansh.carbooking.repository.dao.AvailabilityDao
import com.sudansh.carbooking.repository.dao.CabLiveDao
import com.sudansh.carbooking.repository.dao.DropoffDao
import com.sudansh.carbooking.repository.dao.JoinDao
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.AvailableDropJoin
import com.sudansh.carbooking.repository.entity.CabLive
import com.sudansh.carbooking.repository.entity.DropoffLocation


@Database(entities = [CabLive::class,
    Availability::class,
    DropoffLocation::class,
    AvailableDropJoin::class],
        exportSchema = false,
        version = 1)

@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cabLiveDao(): CabLiveDao
    abstract fun availabilityDao(): AvailabilityDao
    abstract fun dropoffDao(): DropoffDao
    abstract fun joinDao(): JoinDao
}