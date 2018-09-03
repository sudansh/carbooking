package com.sudansh.carbooking.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sudansh.carbooking.repository.dao.CabLiveDao
import com.sudansh.carbooking.repository.entity.CabLive


@Database(entities = [CabLive::class],
        exportSchema = false,
        version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun cabLiveDao(): CabLiveDao
}