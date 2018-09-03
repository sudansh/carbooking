package com.sudansh.carbooking.di

import android.arch.persistence.room.Room
import com.sudansh.carbooking.data.AppExecutors
import com.sudansh.carbooking.repository.AppDatabase
import com.sudansh.carbooking.repository.CabRepository
import org.koin.dsl.module.applicationContext


val localModule = applicationContext {
    bean { AppExecutors() }
    bean { CabRepository(get(), get(), get()) }
    bean {
        Room.databaseBuilder(get(), AppDatabase::class.java, "car-db").build()
    }
    bean { get<AppDatabase>().cabLiveDao() }
}