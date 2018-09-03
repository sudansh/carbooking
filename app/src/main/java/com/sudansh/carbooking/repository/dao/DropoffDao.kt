package com.sudansh.carbooking.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.carbooking.repository.entity.DropoffLocation


@Dao
interface DropoffDao {

    @Query("SELECT * FROM dropofflocation")
    fun findAll(): LiveData<List<DropoffLocation>>

    @Query("SELECT * FROM dropofflocation WHERE dropId= :id")
    fun findById(id: Int): LiveData<DropoffLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DropoffLocation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<DropoffLocation>)
}