package com.sudansh.carbooking.repository

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverter {

    @TypeConverter
    fun fromListDouble(list: List<Double>) = Gson().toJson(list).orEmpty()

    @TypeConverter
    fun toListDouble(value: String): List<Double> =
            Gson().fromJson<List<Double>>(value, object : TypeToken<List<Double>>() {}.type)
}