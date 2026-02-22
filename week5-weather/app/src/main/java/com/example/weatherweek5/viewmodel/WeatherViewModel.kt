package com.example.weatherweek5.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherweek5.data.local.AppDatabase
import com.example.weatherweek5.data.model.WeatherCacheEntity
import com.example.weatherweek5.data.remote.RetrofitInstance
import com.example.weatherweek5.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val loading: Boolean = false,
    val error: String? = null,

    // Nämä näytetään UI:ssa (Roomista!)
    val shownCity: String? = null,
    val tempC: Double? = null,
    val description: String? = null,

    val lastFetchedAtMillis: Long? = null
)

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.get(app).weatherDao()
    private val repo = WeatherRepository(
        api = RetrofitInstance.api,
        dao = dao
    )

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    private var latestCache: WeatherCacheEntity? = null

    init {
        // 1) Kuunnellaan Roomia koko ajan
        viewModelScope.launch {
            repo.observeLatest().collect { entity ->
                latestCache = entity
                _uiState.update { s ->
                    s.copy(
                        shownCity = entity?.city,
                        tempC = entity?.tempC,
                        description = entity?.description,
                        lastFetchedAtMillis = entity?.fetchedAtMillis
                    )
                }
            }
        }
    }

    fun setCity(value: String) {
        _uiState.update { it.copy(city = value) }
    }

    fun fetchWeather() {
        val city = uiState.value.city.trim()
        if (city.isBlank()) {
            _uiState.update { it.copy(error = "Syötä kaupunki") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            try {
                // 2) Välimuisti: jos Room tyhjä tai yli 30 min vanha -> hae API:sta
                val stale = repo.isStale(latestCache, maxAgeMinutes = 30)

                // Lisäbonus: jos käyttäjä hakee eri kaupungin kuin cache -> pakota haku
                val differentCity = latestCache?.city?.equals(city, ignoreCase = true) == false

                if (stale || differentCity) {
                    repo.refreshIfNeeded(city)
                }
                // UI päivittyy automaattisesti kun Room päivittyy (collect)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Haku epäonnistui (tarkista kaupunki / api-key)") }
            } finally {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }
}