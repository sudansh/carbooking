package com.sudansh.carbooking.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.AvailableDropJoin

@Dao
interface AvailabilityDao {

    @Query("SELECT * FROM availability")
    fun findAll(): LiveData<List<Availability>>

    @Query("SELECT * FROM availability WHERE availableId= :id")
    fun findById(id: Int): LiveData<Availability>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Availability)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<AvailableDropJoin>)


}