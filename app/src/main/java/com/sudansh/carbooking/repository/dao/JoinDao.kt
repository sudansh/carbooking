package com.sudansh.carbooking.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.carbooking.repository.entity.Availability
import com.sudansh.carbooking.repository.entity.AvailableDropJoin


@Dao
interface JoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: AvailableDropJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<AvailableDropJoin>)

    @Query("SELECT * FROM availability INNER JOIN availabledropjoin ON availability.availableId = availabledropjoin.dropId WHERE dropId = :availabilityId")
    fun findAvailableById(availabilityId: Int): LiveData<List<Availability>>
}