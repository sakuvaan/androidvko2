package com.example.weatherweek5.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(
    @PrimaryKey val id: Int = 0,
    val city: String,
    val tempC: Double,
    val description: String,
    val fetchedAtMillis: Long
)