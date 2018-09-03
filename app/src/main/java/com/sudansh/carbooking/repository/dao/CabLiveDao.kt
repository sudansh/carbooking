package com.sudansh.carbooking.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sudansh.carbooking.repository.entity.CabLive

@Dao
interface CabLiveDao {

    @Query("SELECT * FROM cablive")
    fun findAll(): LiveData<List<CabLive>>

    @Query("SELECT * FROM cablive WHERE id= :id")
    fun findById(id: Int): LiveData<CabLive>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cabLive: CabLive)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<CabLive>)
}