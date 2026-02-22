package com.example.weatherweek5.data.repository

import com.example.weatherweek5.data.local.WeatherDao
import com.example.weatherweek5.data.model.WeatherCacheEntity
import com.example.weatherweek5.data.remote.WeatherApi
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {
    fun observeLatest(): Flow<WeatherCacheEntity?> = dao.observeLatest()

    suspend fun refreshIfNeeded(city: String, maxAgeMinutes: Long = 30) {
        val now = System.currentTimeMillis()

        val response = api.getWeather(city = city)

        val temp = response.main.temp
        val desc = response.weather.firstOrNull()?.description ?: "-"

        dao.upsert(
            WeatherCacheEntity(
                id = 0,
                city = response.name.ifBlank { city },
                tempC = temp,
                description = desc,
                fetchedAtMillis = now
            )
        )
    }

    fun isStale(entity: WeatherCacheEntity?, maxAgeMinutes: Long = 30): Boolean {
        if (entity == null) return true
        val ageMs = System.currentTimeMillis() - entity.fetchedAtMillis
        val maxMs = maxAgeMinutes * 60_000L
        return ageMs > maxMs
    }
}